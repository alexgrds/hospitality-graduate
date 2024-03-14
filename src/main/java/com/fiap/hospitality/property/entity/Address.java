package com.fiap.hospitality.property.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(name = "address")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String location;
    private String cep;
    private String city;

    @Enumerated(EnumType.STRING)
    private StateEnum state;

    public Address(String location, String cep, String city, StateEnum state) {
        this.location = location;
        this.cep = cep;
        this.city = city;
        this.state = state;
    }

    
}
