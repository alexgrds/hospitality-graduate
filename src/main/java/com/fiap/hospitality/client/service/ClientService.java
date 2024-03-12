package com.fiap.hospitality.client.service;


import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public List<Client> findAll() {
        return repository.findAll();
    }

}
