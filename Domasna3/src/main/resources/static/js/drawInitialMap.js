function drawInitialMap() {
    const OPTIONS = {
        lat: 41.9965,
        lng: 21.4314,
        zoom: 13.75,
        style: "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
    }

    if (GLOBALS.mapCoordinatesFromUrl.lat !== -1) OPTIONS.lat = GLOBALS.mapCoordinatesFromUrl.lat;
    if (GLOBALS.mapCoordinatesFromUrl.lng !== -1) OPTIONS.lng = GLOBALS.mapCoordinatesFromUrl.lng;
    if (GLOBALS.mapCoordinatesFromUrl.zoom !== -1) OPTIONS.zoom = GLOBALS.mapCoordinatesFromUrl.zoom;

    GLOBALS.initialOptions = OPTIONS;

    GLOBALS.map = L.map('mapdiv')

    GLOBALS.map.setView([OPTIONS.lat, OPTIONS.lng], OPTIONS.zoom);

    L.tileLayer(OPTIONS.style, {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(GLOBALS.map);
}
drawInitialMap();

function drawYellowMarkerOnSharedLocation() {
    if (GLOBALS?.mapCoordinatesFromUrl?.lat !== -1 && GLOBALS?.mapCoordinatesFromUrl?.lng !== -1) {
        GLOBALS.profiles.markerOnSharedLocation = L.marker([GLOBALS.mapCoordinatesFromUrl.lat, GLOBALS.mapCoordinatesFromUrl.lng], GLOBALS.initialOptions.zoom).addTo(GLOBALS.map);
        GLOBALS.profiles.markerOnSharedLocation.setIcon(GLOBALS.yellowIcon);
    }
}
drawYellowMarkerOnSharedLocation();