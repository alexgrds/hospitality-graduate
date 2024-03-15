package com.fiap.hospitality.utils;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.RoomTypeEnum;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRequest;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRoomResponse;
import com.fiap.hospitality.property.entity.dto.RoomBathroomResponse;

public abstract class PropertyHelper {
    public static Property createPropertys() {
        return Property.builder().id("1")
            .propertyName("Hotel")
            .amenitiesDescription("Swimming pool")
            .address(AddressHelper.createAddress())
            .rooms(Set.of(RoomHelper.createRooms())).build();
    }

    public static PropertyAddressRequest createPropertyAddressRequest() {
        return new PropertyAddressRequest("Hotel"
            , "Swimming pool"
            , AddressHelper.createAddressRequest());
    }

    public static PropertyAddressRoomResponse createPropertyAddressRoomResponse() {
        return new PropertyAddressRoomResponse("Hotel"
            , "Swimming pool"
            , AddressHelper.createAddress()
            , Set.of(createRoomBathroomResponse()));
    }
    public static RoomBathroomResponse createRoomBathroomResponse() {
        return new RoomBathroomResponse(UUID.randomUUID().toString(), RoomTypeEnum.valueOf("STANDARD_SIMPLE")
            , "Room"
            , BathroomHelper.createBathrooms()
            , 2
            , BigDecimal.valueOf(100.00));
    }

}
