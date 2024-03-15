package com.fiap.hospitality.property.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum RoomTypeEnum {

    STANDARD_SIMPLE,
    STANDARD_COUPLE,
    LUXURY_SIMPLE,
    LUXURY_COUPLE,
    PREMIUM_SIMPLE,
    PREMIUM_COUPLE;

    String name;

    private RoomTypeEnum(String name) {
        this.name = name;
    }

}
