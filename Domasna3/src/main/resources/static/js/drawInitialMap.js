function drawInitialMap() {
    const OPTIONS = {
        lat: 41.9965,
        lng: 21.4314,
        zoom: 13.75,
        style: "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
    }

    GLOBALS.initialOptions = OPTIONS;

    GLOBALS.map = L.map('mapdiv')

    GLOBALS.map.setView([OPTIONS.lat, OPTIONS.lng], OPTIONS.zoom);

    L.tileLayer(OPTIONS.style, {
        maxZoom: 19,
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(GLOBALS.map);
}
drawInitialMap();
