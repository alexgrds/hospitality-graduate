package com.fiap.hospitality.property.entity;

import java.util.HashSet;
import java.util.Set;

import com.fiap.hospitality.property.entity.dto.PropertyAddressRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "properties")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String propertyName;
    private String amenitiesDescription;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "properties_rooms",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<Room> rooms = new HashSet<>();

    public Property(PropertyAddressRequest propertyRequest) {
        this.propertyName = propertyRequest.propertyName();
        this.amenitiesDescription = propertyRequest.amenitiesDescription();
        this.address = propertyRequest.address().toDomain();
    }

}
