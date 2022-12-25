package com.example.diansspring.web.controller.rest;

import com.example.diansspring.model.Facility;
import com.example.diansspring.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "favourites")
public class FavouritesController {
    private final UserService userService;

    public FavouritesController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public List<Facility> updateFavouritesList(@RequestParam Long facilityId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.equals(username, "anonymousUser")) return null;

        return this.userService.updateFavouritesList(username, facilityId);
    }
}
