import { drawMap } from "../drawMap.js";

export function bindPopupsToMarkers() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i].options.marker.bindPopup(GLOBALS.profiles.defaultPopup);
    }

    if (GLOBALS.profiles.clickedFacility !== undefined) {
        openPopup();
    }
}

export function openPopup() {
    setTimeout(() => {
        if (GLOBALS.profiles.clickedFacility !== undefined) {
            GLOBALS.profiles.clickedFacility.options.marker.openPopup();
            setPopupContent();
        }
    }, 10);
}

function setPopupContent() {
    for (let elem of document.getElementsByClassName('markerPopupFacilityName')) elem.innerHTML = GLOBALS.profiles.clickedFacility?.name;

    manageFacilityTypeAndDiscount();

    manageFavouriteButton();

    manageReviewed();

    manageReviews();
}

function manageFacilityTypeAndDiscount() {
    let textForMarkerPopupFacilityType = GLOBALS.profiles.clickedFacility?.facilityType?.replaceAll("_", " ");
    if (parseInt(GLOBALS.profiles.clickedFacility?.discount) !== 0) textForMarkerPopupFacilityType += " - Up to " + GLOBALS.profiles.clickedFacility.discount + "% discounts!";
    for (let elem of document.getElementsByClassName('markerPopupFacilityType')) elem.innerHTML = textForMarkerPopupFacilityType;
}

function manageFavouriteButton() {
    for (let elem of document.getElementsByClassName('markerPopupFavouriteButton')) {
        elem.setAttribute('facility-id', GLOBALS.profiles.clickedFacility?.id);

        if (GLOBALS.favourites?.map(e => e?.id?.toString()).includes(elem.getAttribute('facility-id'))) {
            elem.classList.add('favourited');
        }
        else {
            elem.classList.remove('favourited');
        }

        elem.addEventListener('click', () => {
            $.ajax({
                type: "POST",
                url: "/favourites/add",
                data: {
                    facilityId: elem.getAttribute('facility-id'),
                },
                success: async function(data) {
                    if (data) {
                        GLOBALS.favourites = data;
                        await drawMap();
                    }
                },
                fail: function(xhr, status, error) {
                    console.log(error);
                }
            });
        })
    }
}

function manageReviewed() {
    for (let elem of document.getElementsByClassName('markerPopupReviewButton')) {
        elem.setAttribute('facility-id', GLOBALS.profiles.clickedFacility?.id);

        if (GLOBALS.reviewed?.map(e => e?.facility?.id?.toString()).includes(elem.getAttribute('facility-id'))) {
            elem.classList.add('reviewed');
        }
        else {
            elem.classList.remove('reviewed');
        }

        elem.addEventListener('click', () => {
            let review = 0;
            if (!elem.classList.contains("reviewed")) {
                review = parseInt(prompt("With how many stars are you rating this facility?"));
            }
            else {
                review = parseInt(prompt("(Note: you have already rated this facility. Rating it again will override your old rating!) With how many stars are you rating this facility?"));
            }

            if (isNaN(review) || review < 1 || review > 5) {
                alert('The review must be a whole number between 1 and 5!');
                return ;
            }

            $.ajax({
                type: "POST",
                url: "/reviews/add",
                data: {
                    facilityId: elem.getAttribute('facility-id'),
                    review: review,
                },
                success: async function(data) {
                    if (data) {
                        GLOBALS.reviewed = data;

                        $.ajax({
                            type: "GET",
                            url: "/home/getFacilities",
                            success: async function(data) {
                                if (data) {
                                    for (let i = 0; i < GLOBALS.facilities.length; i++) {
                                        GLOBALS.facilities[i] = {
                                            ...data.filter(e => e.id === GLOBALS.facilities[i].id)[0],
                                            options: GLOBALS.facilities[i].options
                                        }
                                    }
                                    GLOBALS.profiles.clickedFacility = GLOBALS.facilities.filter(e => e.id === GLOBALS.profiles.clickedFacility.id)[0];
                                    await drawMap();
                                }
                            },
                            fail: function(xhr, status, error) {
                                console.log(error);
                            }
                        });

                        await drawMap();
                    }
                },
                fail: function(xhr, status, error) {
                    console.log(error);
                }
            });
        })
    }
}

function manageReviews() {
    if (GLOBALS.profiles.clickedFacility.reviewRatingsCount === 0) {
        for (let elem of document.getElementsByClassName('noReviewsYet')) elem.style.display = 'block';
        for (let elem of document.getElementsByClassName('yellowStars')) elem.style.display = 'none';
        for (let elem of document.getElementsByClassName('whiteStars')) elem.style.display = 'none';
    }
    else {
        for (let elem of document.getElementsByClassName('noReviewsYet')) elem.style.display = 'none';
        for (let elem of document.getElementsByClassName('yellowStars')) elem.style.display = 'flex';
        for (let elem of document.getElementsByClassName('whiteStars')) elem.style.display = 'flex';

        for (let elem of document.getElementsByClassName('markerClickPopup')) {
            const average = GLOBALS.profiles.clickedFacility.reviewRatingsAverage;
            let percentageAverage = (average / 5.0) * 100;
            let fillStars = [];
            for (let i = 0; i < 5; i++) {
                fillStars.push(percentageAverage);
                percentageAverage -= 20;
            }

            let stars = elem.querySelectorAll('.star');
            let starIdx = 0;
            for (let star of stars) {
                star.style.display = 'block';
                star.classList.remove('star-full', 'star-partial', 'star-empty');

                if (fillStars[starIdx] >= 20) {
                    star.classList.add('star-full');
                }
                else if (fillStars[starIdx] <= 0) {
                    star.classList.add('star-empty');
                }
                else {
                    star.classList.add('star-partial');
                    let percentageOfStar = (fillStars[starIdx] / 20.0) * 100;
                    let pixelsOfStar = (percentageOfStar / 100.0) * 20;

                    document.documentElement.style.setProperty('--starPartial', pixelsOfStar + 'px');
                }

                starIdx++;
            }
        }
    }
}
