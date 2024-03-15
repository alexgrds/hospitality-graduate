package com.fiap.hospitality.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.hospitality.booking.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {
}
