function bindPopupsToMarkers() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i].options.marker.bindPopup(document.getElementById('markerClickPopup'));
    }
}
bindPopupsToMarkers();

// Filter based on facility type
function hideThoseNotInChosenFacilityType() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        if (GLOBALS.facilities[i].facilityType !== GLOBALS.profiles.filterByFacilityType) {
            GLOBALS.facilities[i]?.options?.marker?.setOpacity(0);
        }
        else {
            GLOBALS.facilities[i]?.options?.marker?.setOpacity(1);
        }
    }
}

function showAll() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i]?.options?.marker?.setOpacity(1);
    }
}

document.getElementById('filterByFacilityType').addEventListener('change', function() {
    GLOBALS.profiles.filterByFacilityType = document.getElementById('filterByFacilityType').value;

    if (GLOBALS.profiles.filterByFacilityType === "ALL TYPES") {
        showAll();
    }
    else {
        hideThoseNotInChosenFacilityType();
    }
});