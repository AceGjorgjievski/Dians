import { drawMap } from "../drawMap.js";
import { drawRoute, removeRoute } from "../manageMapItems/manageDrawnRoute.js";

document.getElementById('buttonManageDistanceRadius').addEventListener('click', async () => {
    GLOBALS.profiles.distanceRadius = GLOBALS.profiles.distanceRadius ?? 0;

    if (GLOBALS.profiles.distanceRadius === 0) {
        try {
            const answer = parseInt(prompt("At what distance (in meters) near you should we show facilities?"));
            if (isNaN(answer) || answer <= 0) throw "Exception";

            GLOBALS.profiles.distanceRadius = answer;
        } catch (e) {
            alert('Invalid value. It should be a positive whole number.');
        }
    } else {
        GLOBALS.profiles.distanceRadius = 0;
    }

    await drawMap();
})

document.getElementById('buttonManageDrawRoute').addEventListener('click', async () => {
    GLOBALS.profiles.drawnRoute = GLOBALS.profiles.drawnRoute ?? undefined;

    if (GLOBALS.profiles.drawnRoute === undefined) {
        await drawRoute();
    }
    else {
        await removeRoute();
    }

    await drawMap();
})