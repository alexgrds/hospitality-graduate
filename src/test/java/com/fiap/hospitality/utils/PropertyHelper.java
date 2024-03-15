package com.fiap.hospitality.utils;

import com.fiap.hospitality.option.entity.dto.OptionRequest;
import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.RoomTypeEnum;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRequest;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRoomResponse;
import com.fiap.hospitality.property.entity.dto.RoomBathroomResponse;

import java.math.BigDecimal;
import java.util.Set;

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
        return new RoomBathroomResponse(RoomTypeEnum.valueOf("STANDARD_SIMPLE")
            , "Room"
            , BathroomHelper.createBathrooms()
            , 2
            , BigDecimal.valueOf(100.00));
    }

}
