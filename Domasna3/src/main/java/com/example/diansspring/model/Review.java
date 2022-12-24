package com.example.diansspring.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Facility facility;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private float rating;

    private String comment;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateCreated;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateUpdated;

    public Review() {
    }

    public Review(float rating, String comment, Facility facility, User user) {
        this.rating = rating;
        this.comment = comment;
        this.facility = facility;
        this.user = user;
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
