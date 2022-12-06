package com.example.diansspring.service.impl;

import com.example.diansspring.model.Facility;
import com.example.diansspring.repository.FacilityRepository;
import com.example.diansspring.service.FacilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }


    @Override
    public List<Facility> listAll() {
        return this.facilityRepository.findAll();
    }

//    @Override
//    public List<Facility> searchAmenitiesByName(String name) {
//        return this.facilityRepository.findByName(name);
//    }
//
//    @Override
//    public List<Facility> searchAmenitiesByMunicipality(String municipality) {
//        return this.facilityRepository.findByMunicipality(municipality);
//    }
//
//    @Override
//    public List<Facility> searchAmenitiesByRating(int rating) {
//        return this.facilityRepository.findByRating(rating);
//    }
//
//    @Override
//    public Optional<Facility> findById(Long id) {
//        return this.facilityRepository.findById(id);
//    }
}
