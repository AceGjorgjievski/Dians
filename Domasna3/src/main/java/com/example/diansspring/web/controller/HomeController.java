package com.example.diansspring.web.controller;


import com.example.diansspring.model.MapCoordinates;
import com.example.diansspring.model.enums.FacilityType;
import com.example.diansspring.service.FacilityService;
import com.example.diansspring.service.ReviewService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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

        if (this.facilityService.listAll().size() == 0) {
            // Set the headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity
            HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

            // Send the request and get the response
            ResponseEntity<String> response = new RestTemplate().exchange(
                    "http://localhost:9091/admin/update-facilities-in-database", HttpMethod.POST, requestEntity, String.class);
        }

        model.addAttribute("facilities", this.facilityService.listAll());
        model.addAttribute("facilityTypes", Arrays.stream(FacilityType.values()).map(FacilityType::name).collect(Collectors.toList()));

        model.addAttribute("mapCoordinates", new MapCoordinates(lat, lng, zoom));

        return "master-template";
    }
}
