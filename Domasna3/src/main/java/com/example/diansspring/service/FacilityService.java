package com.example.diansspring.service;

import com.example.diansspring.model.Facility;

import java.util.List;
import java.util.Optional;

public interface FacilityService {

    List<Facility> listAll();
    void save(Facility facility);

    Facility findById(Long id);
}
