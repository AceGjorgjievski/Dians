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
    GLOBALS.greenIcon = L.icon({
        iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });
    GLOBALS.blueIcon = L.icon({
        iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
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
            clicked: false,
        }

        GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.redIcon);
        GLOBALS.facilities[i].options.marker.addEventListener('click', function () {
            if (!GLOBALS.facilities[i].options.clicked) {
                GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.blueIcon);

                // define the start and end points of the route
                let start = [GLOBALS?.current?.lat, GLOBALS?.current?.lng];
                let end = [GLOBALS.facilities[i].latitude, GLOBALS.facilities[i].longitude];

                fetch('https://router.project-osrm.org/route/v1/walking/' + start[1] + ',' + start[0] + ';' + end[1] + ',' + end[0])
                    .then(function (response) {
                        return response.json();
                    })
                    .then(function (route) {
                        if (route !== undefined && route.routes[0] !== undefined && route.routes[0].geometry !== undefined) {

                            GLOBALS.current.activePath = L.polyline(route?.routes[0]?.geometry, {
                                color: 'green'
                            })?.addTo(GLOBALS.map);
                        }
                    });
            } else {
                GLOBALS.facilities[i].options.marker.setIcon(GLOBALS.redIcon);
            }
        });
    }
}
dropFacilitiesPins();

function hideThoseNotInRadius(maximumRadialDistanceInMeters) {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        if (GLOBALS.facilities[i]?.options?.marker && GLOBALS?.current?.marker) {
            let point1 = L.latLng(GLOBALS.facilities[i].latitude, GLOBALS.facilities[i].longitude);
            let point2 = L.latLng(GLOBALS.current.lat, GLOBALS.current.lng);

            let distanceInMeters = point1.distanceTo(point2);
            if (distanceInMeters > maximumRadialDistanceInMeters) {
                GLOBALS.facilities[i]?.options?.marker?.setOpacity(0);
            }
            else {
                GLOBALS.facilities[i]?.options?.marker?.setOpacity(1);
            }
        }
    }

    if (GLOBALS?.chosenDistanceCircle !== undefined) GLOBALS.chosenDistanceCircle.remove();

    GLOBALS.chosenDistanceCircle = L.circle([GLOBALS?.current?.lat, GLOBALS?.current?.lng], {
        color: 'green',
        fillColor: '#90EE90',
        fillOpacity: 0.5,
        radius: maximumRadialDistanceInMeters
    }).addTo(GLOBALS.map);
}

async function getLocation() {
    if (navigator.geolocation) {
        return new Promise((resolve, reject) =>
            navigator.geolocation.getCurrentPosition(resolve, reject)
        );
    } else {
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
}

document.getElementById("goToMyLocation").addEventListener('click', async () => {
    getLocation()
        .then((position) => {
            if (GLOBALS?.current?.marker !== undefined) {
                GLOBALS.current.marker.remove();
            }

            GLOBALS.current = {
                lat: position.coords.latitude,
                lng: position.coords.longitude,
                marker: L.marker([position.coords.latitude, position.coords.longitude]).addTo(GLOBALS.map),
            }
            GLOBALS.current.marker.setIcon(GLOBALS.greenIcon);

            GLOBALS.map.panTo([GLOBALS.current.lat, GLOBALS.current.lng], {animate: true});

            hideThoseNotInRadius(1000);
        });
})
