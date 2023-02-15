package com.example.diansspring.service;

import com.example.diansspring.model.Facility;
import com.example.diansspring.model.Review;
import com.example.diansspring.model.User;

import java.util.List;

public interface ReviewService {
    List<Review> listAll();

    void save(Review review);

    Review findById(Long id);

    Review findByUserAndFacility(User user, Facility facility);
}
