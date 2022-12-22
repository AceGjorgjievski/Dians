package com.example.diansspring.service;

import com.example.diansspring.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> listAll();

    void save(Review review);

    Review findById(Long id);
}
