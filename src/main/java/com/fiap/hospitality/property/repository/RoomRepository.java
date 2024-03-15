package com.fiap.hospitality.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.hospitality.property.entity.Room;

public interface RoomRepository extends JpaRepository<Room, String> {
}
