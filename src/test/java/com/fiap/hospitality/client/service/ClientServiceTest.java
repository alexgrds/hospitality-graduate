package com.fiap.hospitality.client.service;

import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.entity.dto.ClientRequest;
import com.fiap.hospitality.client.repository.ClientRepository;
import com.fiap.hospitality.utils.ClientHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class ClientServiceTest {

    private ClientService clientService;

    @Mock
    private ClientRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        clientService = new ClientService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class findClients {

        @Test
        void shouldFindAllClients() {

            List<Client> clients = List.of(ClientHelper.createClients());

            when(repository.findAll())
                .thenReturn(clients);

            var clientList = clientService.findAll();

            assertThat(clientList).hasSize(1);

            verify(repository, times(1)).findAll();

        }

        @Test
        void shouldFindById() {

            Client client = ClientHelper.createClients();

            when(repository.findById(anyString()))
                .thenReturn(Optional.ofNullable(client));

            var clientReturn = clientService.findById(client.getId());

            verify(repository, times(1))
                .findById(client.getId());
            assertThat(clientReturn.getId())
                .isEqualTo(client.getId());

        }
    }

    @Nested
    class createClient {

        @Test
        void shouldCreateClient() {

            Client client = ClientHelper.createClients();
            client.setId(null);
            ClientRequest clientRequest = ClientHelper.createClientRequest();

            when(repository.save(any(Client.class)))
                .thenReturn(client);

            when(repository.findByCpf(anyString()))
                .thenReturn(null);

            clientService.save(clientRequest);

            verify(repository, times(1))
                .save(client);
        }
    }

    @Nested
    class updateClient {

        @Test
        void shouldUpdateClient() {

            Client client = ClientHelper.createClients();
            ClientRequest clientRequest = ClientHelper.createClientRequest();

            when(repository.save(any(Client.class)))
                .thenReturn(client);

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(client));

            Client clientSaved = clientService.update(client.getId(),clientRequest);

            assertThat(clientSaved)
                .isNotNull();
            assertThat(clientSaved.getCpf())
                .isEqualTo(clientRequest.getCpf());

            verify(repository, times(1))
                .save(client);
        }
    }

    @Nested
    class deleteClient {

        @Test
        void shouldDeleteClient() {

            Client client = ClientHelper.createClients();

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(client));

            doNothing().when(repository).deleteById(anyString());

            clientService.deleteById(client.getId());

            verify(repository, times(1))
                .deleteById(client.getId());
        }
    }
}
