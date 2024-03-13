package com.fiap.hospitality.utils;

import com.fiap.hospitality.client.entity.Client;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class ClientHelper {
    public static Client createClients() {
        var timestamp = LocalDateTime.now();
        return Client.builder()
            .id("123")
            .cpf("12345678901")
            .email("teste@gmail.com")
            .birthPlace("São Paulo")
            .birthPlaceAddress("Rua teste")
            .fullName("Teste")
            .passport("123456")
            .phoneNumber("11999999999")
            .birthDate("1990-01-01")
            .build();
        }
}
