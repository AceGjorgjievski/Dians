import { drawMap } from "../drawMap.js";
import { drawRoute, removeRoute } from "../manageMapItems/manageDrawnRoute.js";

document.getElementById('buttonManageDistanceRadius').addEventListener('click', async () => {
    GLOBALS.profiles.distanceRadius = GLOBALS.profiles.distanceRadius ?? 0;

    if (GLOBALS.profiles.distanceRadius === 0) {
        GLOBALS.profiles.distanceRadius = 1400;
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
        removeRoute();
    }

    await drawMap();
})