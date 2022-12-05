package com.example.diansspring.model;


import com.example.diansspring.model.enums.FacilityType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Facility {
    @Id
    private Long id;
    private String name;
    private String address;
    private FacilityType facilityType;
    private String municipality;
    @OneToMany
    private List<Review> reviews;
    private float longitude;
    private float latitude;

    public Facility() {
    }

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
        this.reviews = new ArrayList<>();
    }
}
