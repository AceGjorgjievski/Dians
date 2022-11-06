package Classes.Map.OpenStreetMap;

import Classes.Map.BoundingBox;

public class OpenStreetMap {
    private BoundingBox boundingBox;

    public OpenStreetMap() {

    }
    
    public OpenStreetMap setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;

        return this;
    }

    public String getApiUrlForFacilitiesInBoundingBox() {
        return "https://openstreetmap.org/api/0.6/map?bbox=" +
            this.boundingBox.getLeft() + "," + 
            this.boundingBox.getBottom() + "," + 
            this.boundingBox.getRight() + "," + 
            this.boundingBox.getTop();
    }
}
