import { drawMap } from "../drawMap.js";
import { goToMyLocation } from "../geolocation.js";

for (let button of document.getElementsByClassName("buttonGoToMyLocation")) {
    button.addEventListener('click', goToMyLocation);
}

document.getElementById('filterByFacilityType').addEventListener('change', async () => {
    GLOBALS.profiles.filterByFacilityType = document.getElementById('filterByFacilityType').value;

    await drawMap();
});
