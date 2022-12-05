package com.example.diansspring.model;


import com.example.diansspring.model.enums.FacilityType;
import lombok.Data;


@Data
public class Facility {

    private Long id;
    private String name;
    private String address;
    private FacilityType facilityType;
    private String municipality;
    private int rating; //from 1 to 5
    private float longitude;
    private float latitude;


    public Facility(Long id,
                    String name,
                    String address,
                    FacilityType facilityType,
                    String municipality,
                    float longitude,
                    float latitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.facilityType = facilityType;
        this.municipality = municipality;
        this.longitude = longitude;
        this.latitude = latitude;
        this.rating = -1;
    }
}
