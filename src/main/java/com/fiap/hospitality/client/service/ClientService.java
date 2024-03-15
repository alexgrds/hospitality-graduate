package com.fiap.hospitality.client.service;


import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.entity.dto.ClientRequest;
import com.fiap.hospitality.client.repository.ClientRepository;
import com.fiap.hospitality.exception.NotFoundException;
import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.dto.PropertyRequest;
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

    public Client findById(String clientId) {
        return repository
            .findById(clientId)
            .orElseThrow(() -> new NotFoundException("Could not find any Client given id: " + clientId));
    }

    public void save(ClientRequest clientRequest) {
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
