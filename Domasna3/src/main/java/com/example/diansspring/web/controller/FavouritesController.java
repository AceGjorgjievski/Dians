package com.example.diansspring.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "favourites")
public class FavouritesController {

    @PostMapping("/add")
    public boolean addToFavourites(@RequestParam Long facilityId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.equals(username, "anonymousUser")) return false;



        return true;
    }
}
