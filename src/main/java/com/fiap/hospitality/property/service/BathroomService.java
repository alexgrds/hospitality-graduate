package com.fiap.hospitality.property.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.hospitality.exception.NotFoundException;
import com.fiap.hospitality.property.entity.Bathroom;
import com.fiap.hospitality.property.entity.dto.BathroomRequest;
import com.fiap.hospitality.property.repository.BathroomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class BathroomService {

    private final BathroomRepository repository;

    public List<Bathroom> findAll() {
        return repository.findAll();
    }

    public Bathroom findById(String bathroomId) {
        return repository
                .findById(bathroomId)
                .orElseThrow(() -> new NotFoundException("Could not find any Bathroom given id: " + bathroomId));
    }

    public Bathroom save(BathroomRequest bathRequest) {
        Bathroom bathroom = new Bathroom(bathRequest);
        return repository.save(bathroom);
    }

    public Bathroom update(String id, BathroomRequest updatedBathroomRequest) {
        Bathroom bathroom = findById(id);
        Bathroom updatedBathroom = updatedBathroomRequest.returnEntityUpdated(bathroom);
        return repository.save(updatedBathroom);
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }
}
