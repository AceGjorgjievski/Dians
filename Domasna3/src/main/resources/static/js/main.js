import { manageDropPinIcons } from "./manageMapItems/manageDropPinIcons.js";

import { drawInitialMap } from "./drawInitialMap.js";
import { drawMap } from "./drawMap.js";

import "./domElementsEvents/headerElementsEvents.js";
import "./domElementsEvents/footerElementsEvents.js";

manageDropPinIcons();

drawInitialMap();
await drawMap();

document.getElementById('buttonAddToFavourites').addEventListener('click', () => {
    console.log(GLOBALS.profiles.clickedFacility?.id);

    $.ajax({
        type: "POST",
        url: "/favourites/add",
        data: {
            facilityId: GLOBALS.profiles.clickedFacility?.id,
        },
        success: function(response) {
            console.log(response);
        }
    });
})
