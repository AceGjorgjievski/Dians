import { goToMyLocationIfNeeded } from "./geolocation.js";

export function checkIfFilterByFacilityTypeMatching(facility) {
    if (GLOBALS.profiles.filterByFacilityType !== "ALL_TYPES") {
        if (facility.facilityType !== GLOBALS.profiles.filterByFacilityType) {
            return false;
        }
    }

    return true;
}

export function checkIfRouteIsDrawnToAnotherFacility(facility) {
    if (GLOBALS.profiles.drawnRoute !== undefined && GLOBALS.profiles.clickedFacility !== facility) {
        return true;
    }

    return false;
}

export async function checkIfInChosenDistanceRadius(facility, maximumDistanceRadiusInMeters) {
    if (facility === undefined) return true;

    await goToMyLocationIfNeeded()

    let point1 = L.latLng(facility.latitude, facility.longitude);
    let point2 = L.latLng(GLOBALS.current.lat, GLOBALS.current.lng);

    let distanceInMeters = point1.distanceTo(point2);
    if (distanceInMeters > maximumDistanceRadiusInMeters) {
        return false;
    }

    return true;
}