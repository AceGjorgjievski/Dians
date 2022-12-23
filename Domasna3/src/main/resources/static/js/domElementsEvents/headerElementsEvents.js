import { drawMap } from "../drawMap.js";
import { goToMyLocation } from "../geolocation.js";

document.getElementById("goToMyLocation").addEventListener('click', goToMyLocation);

document.getElementById('buttonManageDistanceRadius').addEventListener('click', async () => {
    GLOBALS.profiles.distanceRadius = GLOBALS.profiles.distanceRadius ?? 0;

    if (GLOBALS.profiles.distanceRadius === 0) {
        GLOBALS.profiles.distanceRadius = 1400;
    } else {
        GLOBALS.profiles.distanceRadius = 0;
    }

    await drawMap();
})

document.getElementById('buttonManageDrawRoute').addEventListener('click', async () => {
    GLOBALS.profiles.doDrawRoute = !(GLOBALS.profiles.doDrawRoute ?? true);

    await drawMap();
})

document.getElementById('filterByFacilityType').addEventListener('change', async () => {
    GLOBALS.profiles.filterByFacilityType = document.getElementById('filterByFacilityType').value;

    await drawMap();
});
