package com.fiap.hospitality.booking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.hospitality.booking.entity.BookedRoom;
import com.fiap.hospitality.booking.entity.Booking;
import com.fiap.hospitality.booking.entity.RoomStatus;
import com.fiap.hospitality.booking.entity.dto.BookingRequest;
import com.fiap.hospitality.booking.entity.dto.BookingResponse;
import com.fiap.hospitality.booking.repository.AvailableRoomRepository;
import com.fiap.hospitality.booking.repository.BookingRepository;
import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.service.ClientService;
import com.fiap.hospitality.exception.NotFoundException;
import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.dto.RoomBathroomResponse;
import com.fiap.hospitality.property.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AvailableRoomRepository availableRoomRepository;
    private final ClientService clientService;
    private final RoomService roomService;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(String bookingId) {
        return bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Could not find any Booking given id: " + bookingId));
    }

    public BookingResponse bookingRoom(@Valid BookingRequest request) {
        getAllBookedRooms(request.getCheckin(), request.getCheckout());
        Client client = clientService.findById(request.getClientId());
        Booking booking = registerBooking(client, request);
        return new BookingResponse(booking);
    }

     private Booking registerBooking(Client client, @Valid BookingRequest request) {
        Booking booking = new Booking(request.getCheckin(),request.getCheckout(), client.getId());
        long numberOfNights = ChronoUnit.DAYS.between(request.getCheckin(), request.getCheckout());
        List<RoomBathroomResponse> rooms = getRoomsById(request.getRoomIds());
        addRoomsToBooking(booking, rooms, numberOfNights);
        return bookingRepository.save(booking);
    }

    private List<RoomBathroomResponse> getRoomsById(List<String> roomIds) {
        List<RoomBathroomResponse> bookedRooms = new ArrayList<>();

        if (roomIds.isEmpty())
            throw new NotFoundException("User must select at least one room");

        roomIds.forEach(roomId -> {
            Room room = roomService.findById(roomId);
            bookedRooms.add(RoomBathroomResponse.fromEntity(room));
        });

        return bookedRooms;
    }

    private List<String> getAllBookedRooms(LocalDate checkin, LocalDate checkout) {
        return availableRoomRepository.findAvailableRoomsBetweenCheckinAndCheckout(checkin, checkout);
    }

    private void addRoomsToBooking(Booking booking, List<RoomBathroomResponse> rooms, long numberOfNights) {
        rooms.forEach(room -> {
            booking.clearRooms();
            booking.addRoomToBooking(new BookedRoom(booking, room.roomId(), RoomStatus.BOOKED));
            booking.sumToBookingPrice(room.pricePerNight().multiply(new BigDecimal(numberOfNights)));
        });
    }
}
