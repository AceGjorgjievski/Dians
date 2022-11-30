package com.example.diansspring.web;


import com.example.diansspring.model.Amenity;
import com.example.diansspring.service.AmenityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/") // home, login?
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public String getAmenitiesPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", error);
            model.addAttribute("errorMsg",error);
        }

        List<Amenity> amenityList = this.amenityService.listAll();
        model.addAttribute("amenities",amenityList);

        return "listAmenities";
    }

}
