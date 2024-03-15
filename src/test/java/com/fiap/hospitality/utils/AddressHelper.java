package com.fiap.hospitality.utils;

import com.fiap.hospitality.property.entity.Address;
import com.fiap.hospitality.property.entity.StateEnum;
import com.fiap.hospitality.property.entity.dto.AddressRequest;

public abstract class AddressHelper {
    public static Address createAddress() {
        return Address.builder().id("1")
            .location("Av. Paulista")
            .cep("01310-100")
            .city("São Paulo")
            .state(StateEnum.SP)
            .build();
    }

    public static AddressRequest createAddressRequest() {
        return new AddressRequest("Av. Paulista"
            , "01310-100"
            , "São Paulo"
            , StateEnum.SP
        );
    }
}
