package com.example.diansspring.model;

import com.example.diansspring.model.enums.FacilityType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
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

    private int reviewRatingsCount;

    private float reviewRatingsAverage;

    @Column(nullable = false)
    private float longitude;

    @Column(nullable = false)
    private float latitude;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateAdded;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateUpdated;

    public Facility() {
    }

    public Facility(Long id,
                    String name,
                    String address,
                    FacilityType facilityType,
                    float latitude,
                    float longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.facilityType = facilityType;
        this.municipality = "";
        this.latitude = latitude;
        this.longitude = longitude;
        this.reviews = new ArrayList<>();
        this.reviewRatingsCount = 0;
        this.reviewRatingsAverage = 0;
        this.dateAdded = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    public static Facility create(String str) {
        String[] arr = str.split(", ");
        return new Facility(Long.parseLong(arr[0]),
                            arr[1],
                            arr[2],
                            FacilityType.valueOf(arr[3].toUpperCase()),
                            Float.parseFloat(arr[4]),
                            Float.parseFloat(arr[5]));
    }
}
