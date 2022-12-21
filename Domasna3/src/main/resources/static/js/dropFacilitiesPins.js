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

                document.getElementById('drawRouteToFacility').style.display = 'block';

                if (GLOBALS.profiles.drawnRoute !== undefined) {
                    GLOBALS.profiles.drawnRoute.remove();
                    GLOBALS.profiles.drawnRoute = undefined;
                    document.getElementById('drawRouteToFacility').innerHTML = "Draw route to selected facility!";
                    showAll();
                }
            } else {
                GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.redIcon);

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
    if (GLOBALS?.chosenDistanceCircle !== undefined) GLOBALS.chosenDistanceCircle.remove();

    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i]?.options?.marker?.setOpacity(1);
    }
}