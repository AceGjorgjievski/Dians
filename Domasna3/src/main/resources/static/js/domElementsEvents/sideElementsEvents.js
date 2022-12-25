import { drawMap } from "../drawMap.js";

if (document.getElementById('buttonShowFavouritesOnly') !== null) {
    document.getElementById('buttonShowFavouritesOnly').addEventListener('click', async () => {
        GLOBALS.profiles.showFavouritesOnly = GLOBALS.profiles.showFavouritesOnly ?? false;

        GLOBALS.profiles.showFavouritesOnly = !GLOBALS.profiles.showFavouritesOnly;
        if (GLOBALS.profiles.showFavouritesOnly) {
            document.getElementById('iconShowFavouritesOnly').classList.add("favouriteButtonActive");
        }
        else {
            document.getElementById('iconShowFavouritesOnly').classList.remove("favouriteButtonActive");
        }

        await drawMap();
    });
}
