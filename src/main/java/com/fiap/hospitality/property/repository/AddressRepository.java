package com.fiap.hospitality.property.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.hospitality.property.entity.Address;

public interface AddressRepository extends JpaRepository<Address, String> {
}
