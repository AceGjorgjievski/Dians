import { drawMap } from "../drawMap.js";
import { goToMyLocation } from "../geolocation.js";

for (let button of document.getElementsByClassName("buttonGoToMyLocation")) {
    button.addEventListener('click', goToMyLocation);
}

document.getElementById('filterByFacilityType').addEventListener('change', async () => {
    GLOBALS.profiles.filterByFacilityType = document.getElementById('filterByFacilityType').value;

    await drawMap();
});

for (let button of document.getElementsByClassName("buttonOpenMobileSideFacilities")) {
    button.addEventListener('click', function() {
        for (let elem of document.getElementsByClassName("facilitiesListFixedContainer")) {
            if (elem.style.display === "none" || !elem.style.display) {
                elem.style.display = "block";
            }
            else {
                elem.style.display = "none";
            }
        }
    });
}
