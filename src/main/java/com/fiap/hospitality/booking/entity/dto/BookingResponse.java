package com.fiap.hospitality.booking.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.hospitality.booking.entity.Booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String codigoReserva;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valorTotalReserva;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate checkin;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate checkout;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idCLiente;

    public BookingResponse(Booking booking) {
        this.codigoReserva = booking.getId();
        this.valorTotalReserva = booking.getBookingPrice();
        this.checkin = booking.getCheckin();
        this.checkout = booking.getCheckout();
        this.idCLiente = booking.getClientId();
    }
}
