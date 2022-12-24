import { goToMyLocationIfNeeded } from "../geolocation.js";

export async function drawRoute() {
    await goToMyLocationIfNeeded()

    if (GLOBALS.profiles.drawnRoute !== undefined) {
        GLOBALS.profiles.drawnRoute.remove();
        GLOBALS.profiles.doDrawRoute = false;
    }

    let start = [GLOBALS?.current?.lat, GLOBALS?.current?.lng];
    let end = [GLOBALS?.profiles?.clickedFacility?.latitude, GLOBALS?.profiles?.clickedFacility?.longitude];

    await fetch('https://graphhopper.com/api/1/route?key=00b41045-4a0c-4f26-8cbe-7cbd3bac986e&vehicle=foot&points_encoded=false&point=' + start[0] + ',' + start[1] + '&point=' + end[0] + ',' + end[1])
        .then(function (response) {
            return response.json();
        })
        .then(async function (data) {
            const coordinates = [];
            data.paths[0].points.coordinates.forEach(coordinatesGroup => {
                coordinates.push([coordinatesGroup[1], coordinatesGroup[0]])
            })

            GLOBALS.profiles.drawnRoute = L.polyline(coordinates, {color: 'green', weight: 6});
            GLOBALS.profiles.drawnRoute.addTo(GLOBALS.map);

            GLOBALS.profiles.doDrawRoute = true;
        });
}

export function removeRoute() {
    if (GLOBALS.profiles.drawnRoute !== undefined) {
        GLOBALS.profiles.drawnRoute.remove();
        GLOBALS.profiles.drawnRoute = undefined;

        GLOBALS.profiles.doDrawRoute = false;
    }
}