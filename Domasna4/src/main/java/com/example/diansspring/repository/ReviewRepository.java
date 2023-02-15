package com.example.diansspring.repository;

import com.example.diansspring.model.Facility;
import com.example.diansspring.model.Review;
import com.example.diansspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndFacility(User user, Facility facility);
}
