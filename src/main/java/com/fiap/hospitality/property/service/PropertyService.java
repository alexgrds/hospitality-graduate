package com.fiap.hospitality.property.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fiap.hospitality.exception.NotFoundException;
import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRequest;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRoomResponse;
import com.fiap.hospitality.property.entity.dto.RoomRequest;
import com.fiap.hospitality.property.repository.PropertyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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

    public Property save(PropertyAddressRequest propertyRequest) {
        Property property = new Property(propertyRequest);
        return repository.save(property);
    }

    public PropertyAddressRoomResponse includeRooms(String propertyId, Set<RoomRequest> roomsRequest) {

        if (roomsRequest.isEmpty())
            throw new IllegalArgumentException("At least one Room must be provided");

        Property property = findById(propertyId);
        Set<Room> rooms = roomsRequest.stream().map(Room::new).collect(Collectors.toSet());
        property.setRooms(rooms);
        Property savedProperty = repository.save(property);
        return PropertyAddressRoomResponse.fromEntity(savedProperty);
    }

    public Property update(String id, PropertyAddressRequest updatedPropertyRequest) {
        Property property = findById(id);
        Property updatedProperty = updatedPropertyRequest.returnEntityUpdated(property);
        return repository.save(updatedProperty);
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }

}
