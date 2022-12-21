package com.example.diansspring.web.controller;


import com.example.diansspring.model.MapCoordinates;
import com.example.diansspring.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    private final FacilityService facilityService;

    public HomeController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public String getHomePage(@RequestParam(required = false) Double lat, @RequestParam(required = false) Double lng, @RequestParam(required = false) Double zoom, Model model) {
        model.addAttribute("pageTitle", "Home - Findify");
        model.addAttribute("mainBodyContent", "home");
        model.addAttribute("facilities", this.facilityService.listAll());

        model.addAttribute("mapCoordinates", new MapCoordinates(lat, lng, zoom));

        return "master-template";
    }
}
