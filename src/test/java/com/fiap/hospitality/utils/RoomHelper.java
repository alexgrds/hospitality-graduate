package com.fiap.hospitality.utils;

import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.RoomTypeEnum;
import com.fiap.hospitality.property.entity.dto.RoomRequest;

import java.math.BigDecimal;
import java.util.Set;

public abstract class RoomHelper {
    public static Room createRooms() {
        return Room.builder().id("1")
            .roomType(RoomTypeEnum.STANDARD_SIMPLE)
            .description("Suite")
            .bathroom(BathroomHelper.createBathrooms())
            .properties(Set.of(PropertyHelper.createPropertys()))
            .totalGuests(2)
            .pricePerNight(BigDecimal.valueOf(100.00))
            .build();
    }

    public static RoomRequest createRoomRequest() {
        return new RoomRequest(RoomTypeEnum.valueOf("STANDARD_SIMPLE")
            , "Suite"
            , 2
            , BigDecimal.valueOf(100.00)
        );
    }
}
