package com.example.diansspring.repository;


import com.example.diansspring.bootstrap.DataHolder;
import com.example.diansspring.model.Amenity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryAmenityRepository {

    public List<Amenity> findAll() {
        return DataHolder.amenities;
    }

    public List<Amenity> findByNameOrAddress(String text) {
        return DataHolder.amenities
                .stream()
                .filter(i -> i.getAddress().equals(text) || i.getName().equals(text))
                .collect(Collectors.toList());
    }

    public List<Amenity> findByName(String name) { //list or one optional object cuz its one name?
        return DataHolder.amenities
                .stream()
                .filter(i -> i.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Amenity> findByMunicipality(String municipality) {
        return DataHolder.amenities
                .stream()
                .filter(i -> i.getMunicipality().equals(municipality))
                .collect(Collectors.toList());
    }

    public List<Amenity> findByRating(int rating) {
        return DataHolder.amenities
                .stream()
                .filter(i -> i.getRating() == rating)
                .collect(Collectors.toList());
    }

    public Optional<Amenity> findById(Long id) {
        return DataHolder.amenities
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }
}
