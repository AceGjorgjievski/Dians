export function drawInitialMap() {
    GLOBALS.defaultOptions = {
        lat: 41.9965,
        lng: 21.4314,
        zoom: 13.75,
        style: "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
    }

    if (GLOBALS.mapCoordinatesFromUrl?.lat !== -1) GLOBALS.defaultOptions.lat = GLOBALS.mapCoordinatesFromUrl.lat;
    if (GLOBALS.mapCoordinatesFromUrl?.lng !== -1) GLOBALS.defaultOptions.lng = GLOBALS.mapCoordinatesFromUrl.lng;
    if (GLOBALS.mapCoordinatesFromUrl?.zoom !== -1) GLOBALS.defaultOptions.zoom = GLOBALS.mapCoordinatesFromUrl.zoom;

    drawMapWithLeaflet();

    drawYellowMarkerIfSharedLocation()
}

function drawMapWithLeaflet() {
    GLOBALS.map = L.map('mapdiv')

    GLOBALS.map.setView([GLOBALS.defaultOptions.lat, GLOBALS.defaultOptions.lng], GLOBALS.defaultOptions.zoom);

    L.tileLayer(GLOBALS.defaultOptions.style, {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(GLOBALS.map);
}

function drawYellowMarkerIfSharedLocation() {
    if (GLOBALS?.mapCoordinatesFromUrl?.lat !== -1 && GLOBALS?.mapCoordinatesFromUrl?.lng !== -1) {
        GLOBALS.profiles.markerOnSharedLocation = L.marker([GLOBALS.mapCoordinatesFromUrl.lat, GLOBALS.mapCoordinatesFromUrl.lng], GLOBALS.defaultOptions.zoom).addTo(GLOBALS.map);
        GLOBALS.profiles.markerOnSharedLocation.setIcon(GLOBALS.yellowIcon);
        GLOBALS.profiles.markerOnSharedLocation.bindPopup("Someone shared this location with you.").openPopup();
    }
}
