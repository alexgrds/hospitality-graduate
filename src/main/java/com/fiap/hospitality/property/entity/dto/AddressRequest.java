package com.fiap.hospitality.property.entity.dto;

import com.fiap.hospitality.property.entity.Address;
import com.fiap.hospitality.property.entity.StateEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AddressRequest(
        @Schema(description = "location to identify the address", example = "Rua Fiap")
        @NotNull(message = "location is mandatory")
        String location,
        @Schema(description = "cep to identify the address", example = "12345-678")
        @NotNull(message = "cep is mandatory")
        String cep,
        @Schema(description = "city to identify the address", example = "São Paulo")
        @NotNull(message = "city is mandatory")
        String city,
        @NotNull(message = "sstate is mandatory")
        StateEnum state) {

        public Address toDomain() {
            return new Address(
                this.cep,
                this.location,
                this.city,
                this.state
            );
        }

}
