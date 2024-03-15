package com.fiap.hospitality.booking.entity.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotNull
    private List<String> roomIds = new ArrayList<>();

    @NotBlank(message = "clientId is mandatory")
    @Schema(description = "Client Id", example = "xxxxx")
    private String clientId;

    @Past(message = "check in should not be set to the paste")
    @Schema(description = "When the client will make the check in", example = "2023-03-15")
    private LocalDate checkin;

    @Past(message = "check out should not be set to the paste")
    @Schema(description = "When the client will make the check in", example = "2023-03-18")
    private LocalDate checkout;

    // public LocalDate getCheckin() throws CheckingInvalidException {
    //     if (this.checkin.isAfter(this.checkout)){
    //         throw new CheckingInvalidException();
    //     }
    //     return checkin;
    // }

    // public LocalDate getCheckout() throws CheckingInvalidException {
    //     if (this.checkout.isBefore(this.checkin)){
    //         throw new CheckingInvalidException();
    //     }
    //     return checkout;
    // }
}
