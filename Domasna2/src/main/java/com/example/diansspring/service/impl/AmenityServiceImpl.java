package com.example.diansspring.service.impl;

import com.example.diansspring.model.Facility;
import com.example.diansspring.repository.InMemoryAmenityRepository;
import com.example.diansspring.service.FacilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AmenityServiceImpl implements FacilityService {

    private final InMemoryAmenityRepository amenityRepository;

    public AmenityServiceImpl(InMemoryAmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }


    @Override
    public List<Facility> listAll() {
        return this.amenityRepository.findAll();
    }

    @Override
    public List<Facility> searchAmenitiesByName(String name) {
        return this.amenityRepository.findByName(name);
    }

    @Override
    public List<Facility> searchAmenitiesByMunicipality(String municipality) {
        return this.amenityRepository.findByMunicipality(municipality);
    }

    @Override
    public List<Facility> searchAmenitiesByRating(int rating) {
        return this.amenityRepository.findByRating(rating);
    }

    @Override
    public Optional<Facility> findById(Long id) {
        return this.amenityRepository.findById(id);
    }
}
