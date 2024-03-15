package com.fiap.hospitality.client.entity.dto;

import com.fiap.hospitality.client.entity.Client;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(title = "ClientRequest", description = "Object that represents a client request")
public record ClientRequest(

    @NotBlank(message = "birthPlace is mandatory")
    @Schema(description = "client's birth place", example = "BRAZIL")
    String birthPlace,

    @NotBlank(message = "cpf is mandatory")
    @Size(max = 11, message = "size must be {max}")
    @Schema(description = "cpf to identify the client", example = "48233025498")
    String cpf,

    @NotBlank(message = "passport is mandatory")
    @Size(max = 9, message = "size must be {max}")
    @Schema(description = "passport to identify the client", example = "123456789")
    String passport,

    @NotBlank(message = "fullName is mandatory")
    @Schema(description = "fullName to identify the client", example = "Jose Fulano")
    String fullName,

    @NotBlank(message = "birthDate is mandatory")
    @Schema(description = "Client's birthDate", example = "12/03/1990")
    String birthDate,

    @NotBlank(message = "birthPlaceAddress is mandatory")
    @Schema(description = "Client's birthPlaceAddress", example = "Rua Diogo vaz 151, SP, São Paulo")
    String birthPlaceAddress,

    @NotBlank(message = "phoneNumber is mandatory")
    @Schema(description = "Client's phoneNumber", example = "11978521588")
    String phoneNumber,

    @NotBlank(message = "email is mandatory")
    @Schema(description = "Client's email", example = "josefulano@gmail.com")
    String email) {

    public Client returnEntityUpdated(Client client) {
        client.setBirthPlace(birthPlace);
        client.setBirthDate(birthDate);
        client.setCpf(cpf);
        client.setPassport(passport);
        client.setFullName(fullName);
        client.setBirthPlaceAddress(birthPlaceAddress);
        client.setPhoneNumber(phoneNumber);
        client.setEmail(email);
        return client;
    }

    public String getCpf() {
        return cpf;
    }
}
