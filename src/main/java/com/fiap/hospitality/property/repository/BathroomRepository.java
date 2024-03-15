package com.fiap.hospitality.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.hospitality.property.entity.Bathroom;

public interface BathroomRepository extends JpaRepository<Bathroom, String> {
}
