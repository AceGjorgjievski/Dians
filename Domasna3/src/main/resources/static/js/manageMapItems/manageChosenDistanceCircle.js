export function createCircle(radius) {
    if (GLOBALS?.chosenDistanceCircle !== undefined) GLOBALS.chosenDistanceCircle.remove();

    GLOBALS.chosenDistanceCircle = L.circle([GLOBALS?.current?.lat, GLOBALS?.current?.lng], {
        color: 'green',
        fillColor: '#90EE90',
        fillOpacity: 0.5,
        radius: radius
    }).addTo(GLOBALS.map);
}

export function removeCircle() {
    if (GLOBALS?.chosenDistanceCircle !== undefined) GLOBALS.chosenDistanceCircle.remove();
}