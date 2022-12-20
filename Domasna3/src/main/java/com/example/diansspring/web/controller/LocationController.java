package com.example.diansspring.web.controller;

import com.example.diansspring.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/location")
public class LocationController {
    private final FacilityService facilityService;

    public LocationController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/go-to-my-location")
    public String goToMyLocation(Model model) {
        model.addAttribute("pageTitle", "Home - Findify");
        model.addAttribute("mainCssFile", "home.css");
        model.addAttribute("mainBodyContent", "home");;
        model.addAttribute("facilities", this.facilityService.listAll());


        return "master-template";
    }
}
