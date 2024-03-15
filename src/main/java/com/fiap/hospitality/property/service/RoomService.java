package com.fiap.hospitality.property.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fiap.hospitality.exception.NotFoundException;
import com.fiap.hospitality.property.entity.Bathroom;
import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.dto.RoomRequest;
import com.fiap.hospitality.property.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class RoomService {

    private final RoomRepository repository;
    private final BathroomService bathroomService;

    public List<Room> findAll() {
        return repository.findAll();
    }

    public Room findById(String roomId) {
        return repository
                .findById(roomId)
                .orElseThrow(() -> new NotFoundException("Could not find any Room given id: " + roomId));
    }

    public Room save(RoomRequest roomRequest) {
        Room room = new Room(roomRequest);
        return repository.save(room);
    }

    public Room update(String id, RoomRequest updatedRoomRequest) {
        Room room = findById(id);
        Room updatedRoom = updatedRoomRequest.returnEntityUpdated(room);
        return repository.save(updatedRoom);
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Room includeBathroom(String roomId, String bathroomId) {

        if (StringUtils.isBlank(bathroomId))
            throw new IllegalArgumentException("At least one Bathroom must be provided");

        Room room = findById(roomId);
        Bathroom bathroom = bathroomService.findById(bathroomId);
        room.setBathroom(bathroom);
        return repository.save(room);
    }
}
