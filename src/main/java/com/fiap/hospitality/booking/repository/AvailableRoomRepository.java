package com.fiap.hospitality.booking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fiap.hospitality.booking.entity.BookedRoom;

public interface AvailableRoomRepository extends JpaRepository<BookedRoom, String> {

    @Query(value = "SELECT br.id FROM booked_room br " +
            "INNER JOIN bookings b ON br.booking_id = b.id " +
            "WHERE :checkin between b.checkin AND b.checkout " +
            "OR :checkout between b.checkin AND b.checkout ", nativeQuery = true)
    List<String> findAvailableRoomsBetweenCheckinAndCheckout(LocalDate checkin, LocalDate checkout);
}
