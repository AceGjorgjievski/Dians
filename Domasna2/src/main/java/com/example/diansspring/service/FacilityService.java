package com.example.diansspring.service;

import com.example.diansspring.model.Facility;

import java.util.List;
import java.util.Optional;

public interface FacilityService {

    List<Facility> listAll();
    List<Facility> searchAmenitiesByName(String name);
    List<Facility> searchAmenitiesByMunicipality(String municipality);
    List<Facility> searchAmenitiesByRating(int rating);

    Optional<Facility> findById(Long id);

}
