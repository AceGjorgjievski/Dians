package com.example.diansspring.web.controller;


import com.example.diansspring.model.Facility;
import com.example.diansspring.model.MapCoordinates;
import com.example.diansspring.model.Review;
import com.example.diansspring.model.enums.FacilityType;
import com.example.diansspring.service.FacilityService;
import com.example.diansspring.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final FacilityService facilityService;
    private final ReviewService reviewService;

    public HomeController(FacilityService facilityService, ReviewService reviewService) {
        this.facilityService = facilityService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public String getHomePage(@RequestParam(required = false) Float lat, @RequestParam(required = false) Float lng, @RequestParam(required = false) Float zoom, Model model) {
        model.addAttribute("pageTitle", "Home - Findify");
        model.addAttribute("mainBodyContent", "home");

        model.addAttribute("facilities", this.facilityService.listAll());
        model.addAttribute("facilityTypes", Arrays.stream(FacilityType.values()).map(FacilityType::name).collect(Collectors.toList()));

        model.addAttribute("mapCoordinates", new MapCoordinates(lat, lng, zoom));

        return "master-template";
    }
}
