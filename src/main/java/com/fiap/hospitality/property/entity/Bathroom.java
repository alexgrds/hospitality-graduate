package com.fiap.hospitality.property.entity;

import com.fiap.hospitality.property.entity.dto.BathroomRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "bathrooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Bathroom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BathTypeEnum bathType;

    @NotBlank
    private String description;

    public Bathroom(BathTypeEnum bathType, String description) {
        this.bathType = bathType;
        this.description = description;
    }

    public Bathroom(BathroomRequest bath) {
        this.bathType = bath.bathType();
        this.description = bath.description();
    }

    public BathroomRequest toDto() {
        return BathroomRequest.fromEntity(this);
    }
}
