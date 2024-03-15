package com.fiap.hospitality.property.entity.dto;

import java.math.BigDecimal;

import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.RoomTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RoomRequest(

    @NotNull(message = "roomType is mandatory")
    @Schema(description = "What's the type of room?", example = "STANDARD_SIMPLE")
    RoomTypeEnum roomType,

    @NotBlank(message = "description is mandatory")
    @Schema(description = "Describe how is the room", example = "1xSofá - 2xPoltronas - 1xFrigobar")
    String description,
    
    @Schema(description = "How many guests the room can afford?", example = "2")
    @Positive
    int totalGuests,

    @NotNull(message = "pricePerNight is mandatory")
    @Schema(description = "How much is it per night?", example = "1000")
    @Positive
    BigDecimal pricePerNight
) {

    public static RoomRequest fromEntity(Room room) {
        return new RoomRequest(room.getRoomType(), room.getDescription(), room.getTotalGuests(), room.getPricePerNight());
    }

    public Room returnEntityUpdated(Room room) {
        room.setRoomType(roomType);
        room.setDescription(description);
        room.setTotalGuests(totalGuests);
        room.setPricePerNight(pricePerNight);
        return room;
    }

    
}
