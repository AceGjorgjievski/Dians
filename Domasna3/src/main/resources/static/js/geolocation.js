async function getLocation() {
    if (navigator.geolocation) {
        return new Promise((resolve, reject) =>
            navigator.geolocation.getCurrentPosition(resolve, reject)
        );
    } else {
        alert("We're sorry, but geolocation is not supported by this browser. Please use a different browser.");
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

// Manage distance radius
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

function hideAllExcept(facility) {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i]?.options?.marker?.setOpacity(0);
    }
    facility.options.marker.setOpacity(1);
}

document.getElementById('hideThoseNotInRadius').addEventListener('click', async () => {
    GLOBALS.profiles.distanceRadius = GLOBALS.profiles.distanceRadius ?? 0;

    if (!GLOBALS.profiles.distanceRadius) {
        if (GLOBALS?.current?.lat === undefined) {
            await goToMyLocation();
        }

        hideThoseNotInRadius(2000);

        GLOBALS.profiles.distanceRadius = 2000;
        document.getElementById('hideThoseNotInRadius').innerHTML = "Stop distance radius limit!";
    } else {
        showAll();

        GLOBALS.profiles.distanceRadius = 0;
        document.getElementById('hideThoseNotInRadius').innerHTML = "Limit to 2000 meters!";
    }
})

// Draw route to facility
document.getElementById('drawRouteToFacility').addEventListener('click', async () => {
    GLOBALS.profiles.drawnRoute = GLOBALS.profiles.drawnRoute ?? undefined;

    if (GLOBALS.profiles.drawnRoute === undefined) {
        await drawRoute();

        document.getElementById('drawRouteToFacility').innerHTML = "Stop showing route!";
    }
    else {
        GLOBALS.profiles.drawnRoute.remove();
        GLOBALS.profiles.drawnRoute = undefined;

        document.getElementById('drawRouteToFacility').innerHTML = "Draw route to selected facility!";
        showAll();
    }
})

async function drawRoute() {
    if (GLOBALS?.current?.lat === undefined) {
        await goToMyLocation();
    }

    let start = [GLOBALS?.current?.lat, GLOBALS?.current?.lng];
    let end = [GLOBALS?.profiles?.clickedFacility?.latitude, GLOBALS?.profiles?.clickedFacility?.longitude];

    await fetch('https://graphhopper.com/api/1/route?key=00b41045-4a0c-4f26-8cbe-7cbd3bac986e&vehicle=foot&points_encoded=false&point=' + start[0] + ',' + start[1] + '&point=' + end[0] + ',' + end[1])
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            const coordinates = [];
            data.paths[0].points.coordinates.forEach(coordinatesGroup => {
                coordinates.push([coordinatesGroup[1], coordinatesGroup[0]])
            })

            GLOBALS.profiles.drawnRoute = L.polyline(coordinates, {color: 'green', weight: 8});
            GLOBALS.profiles.drawnRoute.addTo(GLOBALS.map);
            // GLOBALS.map.fitBounds(GLOBALS.profiles.drawnRoute.getBounds());

            hideAllExcept(GLOBALS.profiles.clickedFacility);
        });
}

// Share my location
document.getElementById('shareMyLocation').addEventListener('click', async () => {
    if (GLOBALS?.current?.lat === undefined) {
        await goToMyLocation();
    }

    GLOBALS.profiles.locationShareLink = window.location.href + '?lat=' + GLOBALS.current.lat + '&lng=' + GLOBALS.current.lng + '&zoom=' + GLOBALS.map.getZoom();

    navigator.clipboard.writeText(GLOBALS.profiles.locationShareLink).then(function() {
        alert('Location sharing link copied to clipboard');
    }, function(err) {
        alert('Could not copy the link automatically to your clipboard. Here, copy it yourself: "' + GLOBALS.profiles.locationShareLink + '"');
    });
})