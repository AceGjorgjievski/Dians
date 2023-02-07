import { goToMyLocationIfNeeded } from "../geolocation.js";
import { drawMap } from "../drawMap.js";

export async function drawRoute() {
    await goToMyLocationIfNeeded()

    if (GLOBALS.profiles.drawnRoute !== undefined) {
        GLOBALS.profiles.drawnRoute.remove();
        GLOBALS.profiles.doDrawRoute = false;
    }

    let start = [GLOBALS?.current?.lat, GLOBALS?.current?.lng];
    let end = [GLOBALS?.profiles?.clickedFacility?.latitude, GLOBALS?.profiles?.clickedFacility?.longitude];

    if (start[0] === undefined || end[0] === undefined) return;

    return new Promise((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: "/routes/getData",
            data: {
                point1: start[0] + ',' + start[1],
                point2: end[0] + ',' + end[1],
            },
            success: async function (strData) {
                if (strData) {
                    let data = JSON.parse(strData);

                    const coordinates = [];
                    data.paths[0].points.coordinates.forEach(coordinatesGroup => {
                        coordinates.push([coordinatesGroup[1], coordinatesGroup[0]])
                    })

                    GLOBALS.profiles.drawnRoute = L.polyline(coordinates, {color: 'green', weight: 6});
                    GLOBALS.profiles.drawnRoute.addTo(GLOBALS.map);

                    bindPopupToRoute(data.paths[0].distance, data.paths[0].time);

                    GLOBALS.profiles.doDrawRoute = true;
                    resolve();
                }
            },
            fail: function (xhr, status, error) {
                console.log(error);
                reject(error);
            }
        });
    });
}

export async function removeRoute() {
    if (GLOBALS.profiles.drawnRoute !== undefined) {
        GLOBALS.profiles.drawnRoute.remove();
        GLOBALS.profiles.drawnRoute = undefined;

        GLOBALS.profiles.doDrawRoute = false;

        await drawMap();
    }
}

function bindPopupToRoute(distanceInMeters, walkingTimeInMs) {
    // write info such as
    // destination: royal burger
    // distance: 1.3km
    // walking time: 13mins

    let distanceInKm = distanceInMeters / 1000.0;
    let walkingTimeInMinutes = walkingTimeInMs / (1000.0 * 60);

    if (GLOBALS.profiles.drawnRoute !== undefined) {
        GLOBALS.profiles.drawnRoute.bindPopup(document.getElementsByClassName('routePopup')[0]?.outerHTML).openPopup();
        for (let elem of document.getElementsByClassName('routeDestination')) elem.innerHTML = GLOBALS.profiles.clickedFacility.name;
        for (let elem of document.getElementsByClassName('routeDistance')) elem.innerHTML = distanceInKm.toFixed(2) + ' km';
        for (let elem of document.getElementsByClassName('routeWalkingTime')) elem.innerHTML = walkingTimeInMinutes.toFixed(0) + ' min';
    }
}