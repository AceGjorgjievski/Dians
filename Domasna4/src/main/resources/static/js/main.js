import { manageDropPinIcons } from "./manageMapItems/manageDropPinIcons.js";

import { drawInitialMap } from "./drawInitialMap.js";
import { drawMap } from "./drawMap.js";

import "./domElementsEvents/headerElementsEvents.js";
import "./domElementsEvents/footerElementsEvents.js";
import "./domElementsEvents/sideElementsEvents.js";

manageDropPinIcons();

drawInitialMap();
await drawMap();
