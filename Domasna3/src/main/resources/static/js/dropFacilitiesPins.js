function dropFacilitiesPins() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i].options = {
            marker: L.marker([GLOBALS.facilities[i].latitude, GLOBALS.facilities[i].longitude]).addTo(GLOBALS.map),
            clicked: false,
        }

        GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.redIcon);
        GLOBALS.facilities[i].options.marker.addEventListener('click', function () {
            if (!GLOBALS.facilities[i].options.clicked) {
                if (GLOBALS.profiles.clickedFacility !== undefined) {
                    GLOBALS.profiles.clickedFacility.options.clicked = false;
                    GLOBALS.profiles.clickedFacility.options.marker.setIcon(GLOBALS.redIcon);

                    GLOBALS.profiles.clickedFacility.options.marker.openPopup();

                    document.getElementById('drawRouteToFacility').style.display = 'none';

                    if (GLOBALS.profiles.drawnRoute !== undefined) {
                        GLOBALS.profiles.drawnRoute.remove();
                        GLOBALS.profiles.drawnRoute = undefined;
                        document.getElementById('drawRouteToFacility').innerHTML = "Draw route to selected facility!";
                        showAll();
                    }
                }

                GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.blueIcon);
                GLOBALS.facilities[i].options.clicked = true;

                GLOBALS.profiles.clickedFacility = GLOBALS.facilities[i];

                document.getElementById('markerPopupFacilityName').innerHTML = GLOBALS.facilities[i].name;
                if (GLOBALS.facilities[i].reviewRatingsCount === 0) {
                    document.getElementById('noReviewsYet').style.display = 'block';

                    let stars = document.querySelectorAll('.star');
                    for (let star of stars) {
                        star.style.display = 'none';
                    }
                }
                else {
                    document.getElementById('noReviewsYet').style.display = 'none';

                    const average = GLOBALS.facilities[i].reviewRatingsAverage;
                    let percentageAverage = (average / 5.0) * 100;
                    let fillStars = [];
                    for (let i = 0; i < 5; i++) {
                        fillStars.push(percentageAverage);
                        percentageAverage -= 20;
                    }

                    let stars = document.querySelectorAll('.star');
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
                GLOBALS.facilities[i].options.marker.closePopup();

                document.getElementById('drawRouteToFacility').style.display = 'block';

                if (GLOBALS.profiles.drawnRoute !== undefined) {
                    GLOBALS.profiles.drawnRoute.remove();
                    GLOBALS.profiles.drawnRoute = undefined;
                    document.getElementById('drawRouteToFacility').innerHTML = "Draw route to selected facility!";
                    showAll();
                }
            } else {
                GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.redIcon);

                GLOBALS.facilities[i].options.marker.openPopup();

                GLOBALS.profiles.clickedFacility = undefined;
                GLOBALS.facilities[i].options.clicked = false;

                document.getElementById('drawRouteToFacility').style.display = 'none';

                if (GLOBALS.profiles.drawnRoute !== undefined) {
                    GLOBALS.profiles.drawnRoute.remove();
                    GLOBALS.profiles.drawnRoute = undefined;
                    document.getElementById('drawRouteToFacility').innerHTML = "Draw route to selected facility!";
                    showAll();
                }
            }
        });
    }
}
dropFacilitiesPins();

function showAll() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i]?.options?.marker?.setOpacity(1);
    }
}