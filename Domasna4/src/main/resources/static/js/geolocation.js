async function getLocation() {
    if (navigator.geolocation) {
        return new Promise((resolve, reject) =>
            navigator.geolocation.getCurrentPosition(resolve, reject)
        );
    } else {
        alert("We're sorry, but geolocation is not supported by this browser. Please use a different browser.");
    }
}

export async function goToMyLocation() {
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

export async function goToMyLocationIfNeeded() {
    if (GLOBALS?.current?.lat === undefined) {
        await goToMyLocation();
    }
}

// Share my location
for (let button of document.getElementsByClassName("buttonShareMyLocation")) {
    button.addEventListener('click', async () => {
        if (GLOBALS?.current?.lat === undefined) {
            await goToMyLocation();
        }

        GLOBALS.profiles.locationShareLink = window.location.href.split("?")[0] + '?lat=' + GLOBALS.current.lat + '&lng=' + GLOBALS.current.lng + '&zoom=' + GLOBALS.map.getZoom();

        navigator.clipboard.writeText(GLOBALS.profiles.locationShareLink).then(function () {
            alert('Location sharing link copied to clipboard');
        }, function (err) {
            alert('Could not copy the link automatically to your clipboard. Here, copy it yourself: "' + GLOBALS.profiles.locationShareLink + '"');
        });
    });
}