package com.fiap.hospitality.property.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.hospitality.exception.NotFoundException;
import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.dto.PropertyRequest;
import com.fiap.hospitality.property.repository.PropertyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;

    public List<Property> findAll() {
        return repository.findAll();
    }

    public Property findById(String propertyId) {
        return repository
                .findById(propertyId)
                .orElseThrow(() -> new NotFoundException("Could not find any Property given id: " + propertyId));
    }

    public Property save(PropertyRequest propertyRequest) {
        Property property = new Property(propertyRequest);
        return repository.save(property);
    }

    public Property update(String id, PropertyRequest updatedPropertyRequest) {
        Property property = findById(id);
        Property updatedProperty = updatedPropertyRequest.returnEntityUpdated(property);
        return repository.save(updatedProperty);
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }

}
