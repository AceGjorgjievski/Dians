package com.example.diansspring.web.controller.rest;

import com.example.diansspring.model.Facility;
import com.example.diansspring.model.Review;
import com.example.diansspring.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "reviews")
public class ReviewsController {
    private final UserService userService;

    public ReviewsController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public List<Review> updateReviewsList(@RequestParam Long facilityId, @RequestParam(required = false) Integer review) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.equals(username, "anonymousUser")) return null;

        return this.userService.updateReviewsList(username, facilityId, review);
    }
}
