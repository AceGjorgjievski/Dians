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
                }

                GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.blueIcon);
                GLOBALS.facilities[i].options.clicked = true;

                GLOBALS.profiles.clickedFacility = GLOBALS.facilities[i];
            } else {
                GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.redIcon);

                GLOBALS.profiles.clickedFacility = undefined;
                GLOBALS.facilities[i].options.clicked = false;
            }
        });
    }
}
dropFacilitiesPins();