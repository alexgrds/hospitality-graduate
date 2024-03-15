package com.fiap.hospitality.client.entity;

import com.fiap.hospitality.client.entity.dto.ClientRequest;
import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "clients")
@Entity(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Client {

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

    public Client(ClientRequest clientRequest) {
        this.birthPlace = clientRequest.birthPlace();
        this.cpf = clientRequest.cpf();
        this.passport = clientRequest.passport();
        this.fullName = clientRequest.fullName();
        this.birthDate = clientRequest.birthDate();
        this.birthPlaceAddress = clientRequest.birthPlaceAddress();
        this.phoneNumber = clientRequest.phoneNumber();
        this.email = clientRequest.email();
    }
}
