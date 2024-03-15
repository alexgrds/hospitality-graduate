package com.fiap.hospitality.booking.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(mappedBy = "booking")
    private List<BookedRoom> rooms = new ArrayList<>();

    private LocalDate checkin;
    private LocalDate checkout;
    private String clientId;

    private BigDecimal bookingPrice = new BigDecimal("0");

     public Booking(LocalDate checkin, LocalDate checkout, String clientId) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.clientId = clientId;
    }

    public void addRoomToBooking(BookedRoom bookedRom){
        this.rooms.add(bookedRom);
    }

    public void clearRooms() {
      this.rooms.clear();
    }

    public void sumToBookingPrice(BigDecimal value){
        this.bookingPrice = this.bookingPrice.add(value);
    }
}
