package com.fiap.hospitality.client.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.entity.dto.ClientRequest;
import com.fiap.hospitality.client.repository.ClientRepository;
import com.fiap.hospitality.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ClientService {

    private final ClientRepository repository;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Client findById(String clientId) {
        return repository
            .findById(clientId)
            .orElseThrow(() -> new NotFoundException("Could not find any Client given id: " + clientId));
    }

    public void save(ClientRequest clientRequest) {

        Client clientCpf = repository.findByCpf(clientRequest.getCpf());
        if(clientCpf != null) {
            throw new IllegalArgumentException("Client with CPF " + clientRequest.getCpf() + " already exists");
        }

        Client client = new Client(clientRequest);
        repository.save(client);
    }

    public Client update(String id, ClientRequest updatedClientRequest) {
        Client client = findById(id);
        Client updatedClient = updatedClientRequest.returnEntityUpdated(client);
        return repository.save(updatedClient);
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }

}
