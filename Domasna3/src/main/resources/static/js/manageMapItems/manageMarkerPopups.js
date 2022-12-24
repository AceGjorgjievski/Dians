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
    for (let elem of document.getElementsByClassName('markerPopupFacilityName')) elem.innerHTML = GLOBALS.profiles.clickedFacility.name;
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
