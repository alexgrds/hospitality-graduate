package com.fiap.hospitality.property.entity.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.fiap.hospitality.property.entity.Address;
import com.fiap.hospitality.property.entity.Property;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "PropertyRequest", description = "Object that represents a client request")
public record PropertyAddressRoomResponse(

    String propertyName,
    String amenitiesDescription,
    Address address,
    Set<RoomBathroomResponse> roomResponse) {

    public static PropertyAddressRoomResponse fromEntity(Property savedProperty) {
        Set<RoomBathroomResponse> rooms = savedProperty.getRooms().stream().map(RoomBathroomResponse::fromEntity).collect(Collectors.toSet());
        return new PropertyAddressRoomResponse(savedProperty.getPropertyName(), savedProperty.getAmenitiesDescription(), savedProperty.getAddress(), rooms);
    }
}
