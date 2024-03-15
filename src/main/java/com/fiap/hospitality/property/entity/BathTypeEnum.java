package com.fiap.hospitality.property.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public enum BathTypeEnum {
    STANDARD,
    LUXURY,
    PREMIUM;

    String name;

    private BathTypeEnum(String name) {
        this.name = name;
    }

}
