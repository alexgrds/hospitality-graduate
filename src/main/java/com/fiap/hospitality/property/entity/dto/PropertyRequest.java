package com.fiap.hospitality.property.entity.dto;

import com.fiap.hospitality.property.entity.Property;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(title = "PropertyRequest", description = "Object that represents a client request")
public record PropertyRequest(

    @NotBlank(message = "amenitiesDescription is mandatory")
    @Schema(description = "properties's amenities", example = "1 x Piscina Adulto, aquecida e coberta /n 1 x Piscina Adulto, não aquecida em área aberta")
    String amenitiesDescription,

    @NotNull
    AddressRequest address) {

    public Property returnEntityUpdated(Property property) {
        property.setAmenitiesDescription(amenitiesDescription);
        property.setAddress(address.toDomain());
        return property;
    }
}
