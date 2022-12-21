package com.example.diansspring.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Facility facility;

    @Column(nullable = false)
    private float rating;

    private String comment;

    public Review() {
    }

    public Review(float rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }
}
