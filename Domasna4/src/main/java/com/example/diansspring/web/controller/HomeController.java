package com.example.diansspring.web.controller;


import com.example.diansspring.model.MapCoordinates;
import com.example.diansspring.model.enums.FacilityType;
import com.example.diansspring.service.FacilityService;
import com.example.diansspring.service.ReviewService;
import com.example.diansspring.service.UserService;
import com.example.diansspring.service.configuration.ConfigurationService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final FacilityService facilityService;
    private final UserService userService;
    private final ConfigurationService configurationService;

    public HomeController(FacilityService facilityService, UserService userService, ConfigurationService configurationService) {
        this.facilityService = facilityService;
        this.userService = userService;
        this.configurationService = configurationService;
    }

    @GetMapping
    public String getHomePage(@RequestParam(required = false) Float lat, @RequestParam(required = false) Float lng, @RequestParam(required = false) Float zoom, Model model) {
        System.out.println(this.configurationService.getBaseUrl());

        model.addAttribute("pageTitle", "Home - Findify");
        model.addAttribute("mainBodyContent", "home");

        if (this.facilityService.listAll().size() == 0) initializeFacilities();

        model.addAttribute("facilities", this.facilityService.listAll());
        model.addAttribute("facilityTypes", Arrays.stream(FacilityType.values()).map(FacilityType::name).collect(Collectors.toList()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!Objects.equals(username, "anonymousUser")) {
            model.addAttribute("favourites", this.userService.findByUsername(username).getFavouriteFacilities());
            model.addAttribute("reviewed", this.userService.findByUsername(username).getReviews());
        }

        model.addAttribute("mapCoordinates", new MapCoordinates(lat, lng, zoom));

        return "master-template";
    }

    private void initializeFacilities() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        ResponseEntity<String> response = new RestTemplate().exchange(
            this.configurationService.getBaseUrl() + "/admin/update-facilities-in-database",
            HttpMethod.POST,
            requestEntity,
            String.class
        );
    }
}
