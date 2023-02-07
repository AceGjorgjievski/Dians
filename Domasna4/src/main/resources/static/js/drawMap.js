import {
    checkIfRouteIsDrawnToAnotherFacility,
    checkIfFilterByFacilityTypeMatching,
    checkIfInChosenDistanceRadius,
} from "./checks.js";
import { createCircle, removeCircle } from "./manageMapItems/manageChosenDistanceCircle.js";
import { drawRoute, removeRoute } from "./manageMapItems/manageDrawnRoute.js";
import { goToMyLocationIfNeeded } from "./geolocation.js";
import { addMarkerClickEvent, unclickMarker } from "./domElementsEvents/mapMarkersEvents.js";
import { bindPopupsToMarkers, setPopupContent } from "./manageMapItems/manageMarkerPopups.js";

export async function drawMap() {
    setDefaultGlobalsProfile();

    await drawFacilities();

    drawSideFacilitiesList();

    await drawRequisites();
}

function setDefaultGlobalsProfile() {
    GLOBALS.profiles.distanceRadius = GLOBALS.profiles.distanceRadius ?? 0;
    GLOBALS.profiles.doDrawRoute = GLOBALS.profiles.doDrawRoute ?? false;

    GLOBALS.profiles.filterByFacilityType = GLOBALS.profiles.filterByFacilityType ?? "ALL_TYPES";
    GLOBALS.profiles.clickedFacility = GLOBALS.profiles.clickedFacility ?? undefined;

    GLOBALS.profiles.defaultPopup = document.getElementsByClassName('markerClickPopup')[0]?.outerHTML ?? undefined;
}

async function drawFacilities(options = {}) {
    if (GLOBALS.profiles.distanceRadius !== 0) {
        await goToMyLocationIfNeeded();
    }

    await removeOldRequisites();

    removeOldMarkers();

    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        let current = GLOBALS.facilities[i];

        if (GLOBALS.profiles.distanceRadius !== 0 && !await checkIfInChosenDistanceRadius(current, GLOBALS.profiles.distanceRadius)) continue;

        if (!checkIfFilterByFacilityTypeMatching(current)) continue;

        if (checkIfRouteIsDrawnToAnotherFacility(current)) continue;

        drawFacility(current);
    }

    bindPopupsToMarkers();
}

function drawSideFacilitiesList() {
    let facilitiesSortedByRating = GLOBALS.facilities.sort((a, b) => b.reviewRatingsAverage - a.reviewRatingsAverage);

    if (GLOBALS.profiles.showFavouritesOnly) {
        facilitiesSortedByRating = facilitiesSortedByRating.filter(e => GLOBALS.favourites.map(f => f.id).includes(e.id));
    }

    let parent = document.getElementsByClassName("facilitiesListContainer")[0];
    while (parent.firstChild) {
        parent.firstChild.remove();
    }

    if (GLOBALS.profiles.clickedFacility !== undefined) {
        let clickedFacilityAsArr = facilitiesSortedByRating.filter(e => GLOBALS.profiles.clickedFacility.id === e.id);
        let otherFacilities = facilitiesSortedByRating.filter(e => GLOBALS.profiles.clickedFacility.id !== e.id);

        facilitiesSortedByRating = clickedFacilityAsArr.concat(otherFacilities);
    }

    for (let i = 0; i < facilitiesSortedByRating.length; i++) {
        let current = facilitiesSortedByRating[i];

        let facilitiesListItem = document.createElement('div');
        parent.appendChild(facilitiesListItem);

        facilitiesListItem.innerHTML = document.getElementsByClassName("facilitiesListItemTemplate")[0].innerHTML;
        facilitiesListItem.className = document.getElementsByClassName("facilitiesListItemTemplate")[0].className;
        facilitiesListItem.setAttribute('data-facility-id', current.id);
    }

    for (let elem of document.getElementsByClassName("facilitiesListItemTemplate")) {
        const facilityId = elem.getAttribute("data-facility-id");
        if (facilityId !== null) {
            setPopupContent({
                facility: GLOBALS.facilities.filter(e => e.id.toString() === facilityId)[0],
                classNamePrefix: "facilitiesListItem",
                parent: elem
            })
        }
    }
}

async function drawRequisites() {
    manageCircleDrawing();

    await manageRouteDrawing();
}

function manageCircleDrawing() {
    if (GLOBALS.profiles.distanceRadius !== 0) {
        createCircle(GLOBALS.profiles.distanceRadius);
        document.getElementById('buttonManageDistanceRadius').innerHTML = "Turn off 'Near me'";
    }
    else {
        removeCircle();
        document.getElementById('buttonManageDistanceRadius').innerHTML = "Near me";
    }
}

async function manageRouteDrawing() {
    if (GLOBALS.profiles.clickedFacility !== undefined) {
        document.getElementById('buttonManageDrawRoute').style.display = "block";
    }
    else {
        document.getElementById('buttonManageDrawRoute').style.display = "none";
    }

    if (GLOBALS.profiles.doDrawRoute !== false) {
        await drawRoute();
        document.getElementById('buttonManageDrawRoute').innerHTML = "Turn off 'Directions'";
    }
    else {
        await removeRoute();
        document.getElementById('buttonManageDrawRoute').innerHTML = "Directions";
    }
}

function drawFacility(current) {
    current.options = {
        marker: L.marker([current.latitude, current.longitude]).addTo(GLOBALS.map),
    }

    if (GLOBALS.profiles?.clickedFacility === current) {
        current.options.marker.setIcon(GLOBALS.blueIcon);
    }
    else {
        current.options.marker.setIcon(GLOBALS.redIcon);
    }

    addMarkerClickEvent(current);
}

async function removeOldRequisites() {
    const check1 = await checkIfInChosenDistanceRadius(GLOBALS.profiles.clickedFacility, GLOBALS.profiles.distanceRadius);
    const check2 = checkIfFilterByFacilityTypeMatching(GLOBALS.profiles.clickedFacility);

    if (check1 && check2) return;

    await unclickMarker();
    await removeRoute();
}

function removeOldMarkers() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        let current = GLOBALS.facilities[i];

        if (current?.options?.marker !== undefined) {
            current.options.marker.remove();
        }
    }
}