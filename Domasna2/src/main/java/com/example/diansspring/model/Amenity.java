package com.example.diansspring.model;


import com.example.diansspring.model.enums.AmenityType;
import lombok.Data;


@Data
public class Amenity {

    private Long id;
    private String name;
    private String address;
    private AmenityType amenityType;
    private String municipality;
    private int rating; //from 1 to 5
    private float longitude;
    private float latitude;


    public Amenity(Long id,
                   String name,
                   String address,
                   AmenityType amenityType,
                   String municipality,
                   float longitude,
                   float latitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.amenityType = amenityType;
        this.municipality = municipality;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rating = 3; //default
    }
}
