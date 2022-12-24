import { manageDropPinIcons } from "./manageMapItems/manageDropPinIcons.js";
import { bindPopupsToMarkers } from "./manageMapItems/manageMarkerPopups.js";

import { drawInitialMap } from "./drawInitialMap.js";
import { drawMap } from "./drawMap.js";

import "./domElementsEvents/headerElementsEvents.js";
import "./domElementsEvents/footerElementsEvents.js";

manageDropPinIcons();

drawInitialMap();
await drawMap();