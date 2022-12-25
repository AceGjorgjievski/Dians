import { drawMap } from "../drawMap.js";
import { goToMyLocation } from "../geolocation.js";

document.getElementById("goToMyLocation").addEventListener('click', goToMyLocation);

document.getElementById('filterByFacilityType').addEventListener('change', async () => {
    GLOBALS.profiles.filterByFacilityType = document.getElementById('filterByFacilityType').value;

    await drawMap();
});
