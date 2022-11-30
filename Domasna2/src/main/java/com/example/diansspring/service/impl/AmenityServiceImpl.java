package com.example.diansspring.service.impl;

import com.example.diansspring.model.Amenity;
import com.example.diansspring.repository.InMemoryAmenityRepository;
import com.example.diansspring.service.AmenityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AmenityServiceImpl implements AmenityService {

    private final InMemoryAmenityRepository amenityRepository;

    public AmenityServiceImpl(InMemoryAmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }


    @Override
    public List<Amenity> listAll() {
        return this.amenityRepository.findAll();
    }

    @Override
    public List<Amenity> searchAmenitiesByName(String name) {
        return this.amenityRepository.findByName(name);
    }

    @Override
    public List<Amenity> searchAmenitiesByMunicipality(String municipality) {
        return this.amenityRepository.findByMunicipality(municipality);
    }

    @Override
    public List<Amenity> searchAmenitiesByRating(int rating) {
        return this.amenityRepository.findByRating(rating);
    }

    @Override
    public Optional<Amenity> findById(Long id) {
        return this.amenityRepository.findById(id);
    }
}
