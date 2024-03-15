package com.fiap.hospitality.utils;

import com.fiap.hospitality.property.entity.BathTypeEnum;
import com.fiap.hospitality.property.entity.Bathroom;
import com.fiap.hospitality.property.entity.dto.BathroomRequest;

public abstract class BathroomHelper {
    public static Bathroom createBathrooms() {
        return Bathroom.builder().id("1")
            .bathType(BathTypeEnum.STANDARD)
            .description("Big")
            .build();
    }

    public static BathroomRequest createBathroomRequest() {
        return new BathroomRequest(BathTypeEnum.STANDARD, "Big");
    }
}
