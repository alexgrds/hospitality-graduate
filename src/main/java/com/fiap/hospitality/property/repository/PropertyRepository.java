package com.fiap.hospitality.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.hospitality.property.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, String> {
}
