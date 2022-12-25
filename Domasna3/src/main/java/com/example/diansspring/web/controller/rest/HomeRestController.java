package com.example.diansspring.web.controller.rest;

import com.example.diansspring.model.Facility;
import com.example.diansspring.service.FacilityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/home")
public class HomeRestController {
    private final FacilityService facilityService;

    public HomeRestController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("getFacilities")
    List<Facility> getFacilities() {
        return this.facilityService.listAll();
    }
}
