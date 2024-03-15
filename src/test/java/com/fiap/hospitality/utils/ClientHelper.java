package com.fiap.hospitality.utils;

import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.entity.dto.ClientRequest;

import java.time.LocalDateTime;

public abstract class ClientHelper {
    public static Client createClients() {
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

    public static ClientRequest createClientRequest() {
        return new ClientRequest("São Paulo"
            , "12345678901"
            , "123456"
            , "Teste"
            , "1990-01-01"
            , "Rua teste"
            , "11999999999"
            , "teste@gmail.com");
    }
}
