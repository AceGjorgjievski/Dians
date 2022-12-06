package com.example.diansspring.model;

import com.example.diansspring.model.enums.FacilityType;
import jakarta.persistence.Column;
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private FacilityType facilityType;

    private String municipality;

    @OneToMany(mappedBy = "facility")
    private List<Review> reviews;

    @Column(nullable = false)
    private float longitude;

    @Column(nullable = false)
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
