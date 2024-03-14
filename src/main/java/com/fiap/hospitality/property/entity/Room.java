package com.fiap.hospitality.property.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(name = "rooms")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private RoomTypeEnum roomType;

    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Bathroom bathroom;

    @ManyToMany(mappedBy = "rooms", cascade = CascadeType.PERSIST)
    private Set<Property> properties = new HashSet<>();

    private int totalGuests;
    private BigDecimal pricePerNight;

}
