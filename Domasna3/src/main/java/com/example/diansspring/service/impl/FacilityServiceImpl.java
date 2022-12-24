package com.example.diansspring.service.impl;

import com.example.diansspring.model.Facility;
import com.example.diansspring.repository.FacilityRepository;
import com.example.diansspring.service.FacilityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<Facility> listAll() {
        return this.facilityRepository.findAll().stream().peek(e -> e.setReviews(e.getReviews().stream().peek(r -> r.setFacility(null)).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    @Override
    public void save(Facility facility) {
        facilityRepository.save(facility);
    }

    @Override
    public Facility findById(Long id) {
        return this.facilityRepository.findById(id).orElse(null);
    }

}
