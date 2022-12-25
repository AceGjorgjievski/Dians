package com.example.diansspring.model;

import com.example.diansspring.model.enums.FacilityType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "facility")
    @JsonIgnore
    private List<Review> reviews;

    private int reviewRatingsCount;

    private float reviewRatingsAverage;

    @Column(nullable = false)
    private float longitude;

    @Column(nullable = false)
    private float latitude;

    private int discount;

    @ManyToMany(mappedBy = "favouriteFacilities")
    @JsonIgnore
    private List<User> favouritedByUsers;

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
                    float longitude,
                    int discount) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.facilityType = facilityType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.discount = discount;
        this.reviews = new ArrayList<>();
        this.reviewRatingsCount = 0;
        this.reviewRatingsAverage = 0;
        this.dateAdded = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
        this.favouritedByUsers = new ArrayList<>();
    }

    public static Facility create(String str) {
        String[] arr = str.split(", ");
        return new Facility(Long.parseLong(arr[0]),
                arr[1],
                arr[2],
                FacilityType.valueOf(arr[3].toUpperCase()),
                Float.parseFloat(arr[4]),
                Float.parseFloat(arr[5]),
                Integer.parseInt(arr[6]));
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", facilityType=" + facilityType +
                ", reviewRatingsCount=" + reviewRatingsCount +
                ", reviewRatingsAverage=" + reviewRatingsAverage +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", discount=" + discount +
                ", dateAdded=" + dateAdded +
                ", dateUpdated=" + dateUpdated +
                '}';
    }
}
