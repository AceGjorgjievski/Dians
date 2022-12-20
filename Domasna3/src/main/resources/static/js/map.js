const OPTIONS = {
    lat: 41.9965,
    lng: 21.4314,
    zoom: 13.75,
    style: "https://tile.openstreetmap.org/{z}/{x}/{y}.png"
}

const GLOBALS = {}
GLOBALS.facilities = facilities;

GLOBALS.map = L.map('mapdiv')

GLOBALS.map.setView([OPTIONS.lat, OPTIONS.lng], OPTIONS.zoom);

L.tileLayer(OPTIONS.style, {
    maxZoom: 19,
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(GLOBALS.map);

function createRedDropPinIcon() {
    GLOBALS.redIcon = L.icon({
        iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });
}
createRedDropPinIcon();

function dropFacilitiesPins() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i].options = {
            marker: L.marker([GLOBALS.facilities[i].latitude, GLOBALS.facilities[i].longitude]).addTo(GLOBALS.map),
            visible: true,
            clicked: false,
        }

        GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.redIcon);
    }
}
dropFacilitiesPins();

// function showFacility(facility) {
//     fill(255, 0, 0, facility.alpha);
//     ellipse(facility.x, facility.y, facility.size, facility.size);
// }
//
// function setup() {
//     canvas = createCanvas(windowWidth - 10, windowHeight - 100);
//     trainMap = mappa.tileMap(options);
//     trainMap.overlay(canvas);
//
//     trainMap.onChange(drawPoints);
// }
//
// function drawPoints() {
//     clear();
//
//     fill(255, 0, 0, 100); // (r,g,b, alpha)
//     let size = 50;
//     size = map(size, 558, 60000000, 1, 70) + trainMap.zoom();
//
//     for (let i = 0; i < facilities.length; i++) {
//         let px = facilities[i].latitude;
//         let py = facilities[i].longitude;
//         const pix = trainMap.latLngToPixel(px, py);
//
//         facilities[i].options = {
//             x: pix.x,
//             y: pix.y,
//             visible: true,
//             clicked: false,
//             size: size,
//             alpha: 100,
//         }
//
//         showFacility(facilities[i].options);
//     }
// }
//
// var x = document.getElementById("demo");
//
// async function getLocation() {
//     if (navigator.geolocation) {
//         return new Promise((resolve, reject) =>
//             navigator.geolocation.getCurrentPosition(resolve, reject)
//         );
//     } else {
//         x.innerHTML = "Geolocation is not supported by this browser.";
//     }
// }
//
// var currentPosition = {};
// document.getElementById("goToMyLocation").addEventListener('click', async () => {
//     getLocation()
//         .then((position) => {
//             currentPosition = {
//                 x: position.coords.latitude,
//                 y: position.coords.longitude
//             }
//
//             mappa.tileMap({
//                 ...options,
//                 lat: currentPosition.x,
//                 lng: currentPosition.y,
//             });
//         });
// })
