package com.fiap.hospitality.client.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(name = "clients")
@Entity(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Client  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String birthPlace;
    private String cpf;
    private String passport;
    private String fullName;
    private String birthDate;
    private String birthPlaceAddress;
    private String phoneNumber;
    private String email;

}
