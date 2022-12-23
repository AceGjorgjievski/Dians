import { openPopup } from "../manageMapItems/manageMarkerPopups.js";

export function addMarkerClickEvent(facility) {
    facility.options.marker.addEventListener('click', async () => {
        if (facility.options.marker.getIcon() == GLOBALS.redIcon) {
            if (GLOBALS.profiles.clickedFacility !== undefined) {
                unclickMarker();
            }

            clickMarker(facility);
        }
        else {
            unclickMarker();
        }
    })
}

function clickMarker(facility) {
    GLOBALS.profiles.clickedFacility = facility;

    facility.options.marker.setIcon(GLOBALS.blueIcon);
    openPopup();
}

export function unclickMarker() {
    if (GLOBALS.profiles.clickedFacility !== undefined) {
        GLOBALS.profiles.clickedFacility.options.marker.setIcon(GLOBALS.redIcon);
        GLOBALS.profiles.clickedFacility = undefined;
    }
}