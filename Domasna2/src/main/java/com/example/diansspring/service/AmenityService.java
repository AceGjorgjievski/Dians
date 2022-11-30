package com.example.diansspring.service;

import com.example.diansspring.model.Amenity;

import java.util.List;
import java.util.Optional;

public interface AmenityService {

    List<Amenity> listAll();
    List<Amenity> searchAmenitiesByName(String name);
    List<Amenity> searchAmenitiesByMunicipality(String municipality);
    List<Amenity> searchAmenitiesByRating(int rating);

    Optional<Amenity> findById(Long id);

}
