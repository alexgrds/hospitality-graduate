package com.fiap.hospitality.property.entity.dto;

import com.fiap.hospitality.property.entity.BathTypeEnum;
import com.fiap.hospitality.property.entity.Bathroom;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BathroomRequest(
    @NotNull
    BathTypeEnum bathType,
    
    @NotBlank(message = "description is mandatory")
    @Schema(description = "Describe how is the bathroom", example = "Box com Ducha, Privada, Ducha higiênica e pia com espelho")
    String description) {

    public Bathroom toDomain() {
        return new Bathroom(
            this.bathType,
            this.description
        );
    }

    public static BathroomRequest fromEntity (Bathroom bathroom) {
        return new BathroomRequest(bathroom.getBathType(), bathroom.getDescription());
    }

    public Bathroom returnEntityUpdated(Bathroom bath) {
        bath.setBathType(bathType);
        bath.setDescription(description);
        return bath;
    }
    
}
