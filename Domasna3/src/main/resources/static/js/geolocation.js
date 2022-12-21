async function getLocation() {
    if (navigator.geolocation) {
        return new Promise((resolve, reject) =>
            navigator.geolocation.getCurrentPosition(resolve, reject)
        );
    } else {
        window.alert("We're sorry, but geolocation is not supported by this browser. Please use a different browser.");
    }
}

async function goToMyLocation() {
    await getLocation()
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
        });
}

document.getElementById("goToMyLocation").addEventListener('click', goToMyLocation);

// Manage Distance Radius
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

function showAll() {
    if (GLOBALS?.chosenDistanceCircle !== undefined) GLOBALS.chosenDistanceCircle.remove();

    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i]?.options?.marker?.setOpacity(1);
    }
}

document.getElementById('hideThoseNotInRadius').addEventListener('click', async () => {
    GLOBALS.profiles.distanceRadius = GLOBALS.profiles.distanceRadius ?? false;

    if (!GLOBALS.profiles.distanceRadius) {
        if (GLOBALS?.current?.lat === undefined) {
            await goToMyLocation();
        }

        hideThoseNotInRadius(2000);

        GLOBALS.profiles.distanceRadius = true;
        document.getElementById('hideThoseNotInRadius').innerHTML = "Stop distance radius limit!";
    } else {
        showAll();

        GLOBALS.profiles.distanceRadius = false;
        document.getElementById('hideThoseNotInRadius').innerHTML = "Limit to 2000 meters!";
    }
})
