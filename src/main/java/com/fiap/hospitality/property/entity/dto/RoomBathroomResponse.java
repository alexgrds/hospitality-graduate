package com.fiap.hospitality.property.entity.dto;

import java.math.BigDecimal;

import com.fiap.hospitality.property.entity.Bathroom;
import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.RoomTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "PropertyRequest", description = "Object that represents a client request")
public record RoomBathroomResponse(

        String roomId,
        RoomTypeEnum roomType,
        String description,
        Bathroom bathroom,
        int totalGuests,
        BigDecimal pricePerNight) {

    public static RoomBathroomResponse fromEntity(Room savedRoom) {
        return new RoomBathroomResponse(savedRoom.getId(),savedRoom.getRoomType(), savedRoom.getDescription(), savedRoom.getBathroom(),
                savedRoom.getTotalGuests(), savedRoom.getPricePerNight());
    }
}
