package com.example.diansspring.service.impl;

import com.example.diansspring.model.Facility;
import com.example.diansspring.model.Review;
import com.example.diansspring.model.User;
import com.example.diansspring.repository.ReviewRepository;
import com.example.diansspring.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> listAll() {
        return this.reviewRepository.findAll();
    }

    @Override
    public void save(Review review) {
        this.reviewRepository.save(review);
    }

    @Override
    public Review findById(Long id) {
        return this.reviewRepository.findById(id).orElse(null);
    }

    @Override
    public Review findByUserAndFacility(User user, Facility facility) {
        return this.reviewRepository.findByUserAndFacility(user, facility);
    }
}
