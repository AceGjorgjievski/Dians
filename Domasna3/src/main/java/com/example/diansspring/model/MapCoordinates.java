package com.example.diansspring.model;

import lombok.Data;

import java.util.Objects;

@Data
public class MapCoordinates {
    private float lat;
    private float lng;
    private float zoom;

    public MapCoordinates(Float lat, Float lng, Float zoom) {
        this.lat = Objects.equals(lat, null) ? -1 : lat;
        this.lng = Objects.equals(lng, null) ? -1 : lng;
        this.zoom = Objects.equals(zoom, null) ? -1 : zoom;
    }
}
