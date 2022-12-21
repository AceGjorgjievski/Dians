function bindPopupsToMarkers() {
    for (let i = 0; i < GLOBALS.facilities.length; i++) {
        GLOBALS.facilities[i].options.marker.bindPopup(document.getElementById('markerClickPopup'));
    }
}
bindPopupsToMarkers();
