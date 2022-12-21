package com.example.diansspring.model;

import lombok.Data;

import java.util.Objects;

@Data
public class MapCoordinates {
    private double lat;
    private double lng;
    private double zoom;

    public MapCoordinates(Double lat, Double lng, Double zoom) {
        this.lat = Objects.equals(lat, null) ? -1 : lat;
        this.lng = Objects.equals(lng, null) ? -1 : lng;
        this.zoom = Objects.equals(zoom, null) ? -1 : zoom;
    }
}
