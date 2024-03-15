package com.fiap.hospitality.property.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fiap.hospitality.property.entity.dto.RoomRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Table(name = "rooms")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private RoomTypeEnum roomType;

    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Bathroom bathroom;

    @ManyToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private Set<Property> properties = new HashSet<>();

    private int totalGuests;
    private BigDecimal pricePerNight;

    public Room(RoomTypeEnum roomType, String description, Bathroom bathroom, Set<Property> properties, int totalGuests,
            BigDecimal pricePerNight) {
        this.roomType = roomType;
        this.description = description;
        this.bathroom = bathroom;
        this.properties = properties;
        this.totalGuests = totalGuests;
        this.pricePerNight = pricePerNight;
    }

    public Room(RoomRequest rooms) {
        this.roomType = rooms.roomType();
        this.description = rooms.description();
        this.totalGuests = rooms.totalGuests();
        this.pricePerNight = rooms.pricePerNight();
    }

    public RoomRequest toDto() {
        return RoomRequest.fromEntity(this);
    }

}
