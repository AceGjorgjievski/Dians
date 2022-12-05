package com.example.diansspring.web.controller;


import com.example.diansspring.model.Facility;
import com.example.diansspring.service.FacilityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = {"", "/home"}) // home, login?
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public String getAmenitiesPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", error);
            model.addAttribute("errorMsg",error);
        }

        List<Facility> amenityList = this.facilityService.listAll();
        model.addAttribute("amenities",amenityList);

        return "home";
    }

}
