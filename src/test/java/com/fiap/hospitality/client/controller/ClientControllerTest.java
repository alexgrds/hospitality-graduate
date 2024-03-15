package com.fiap.hospitality.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.entity.dto.ClientRequest;
import com.fiap.hospitality.client.service.ClientService;
import com.fiap.hospitality.utils.ClientHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        ClientController clientController = new ClientController(clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
            .addFilter((request, response, chain) -> {
                response.setCharacterEncoding("UTF-8");
                chain.doFilter(request, response);
            }, "/*")
            .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class ListClients {

        @Test
        void shouldListClients() throws Exception {
            var client = ClientHelper.createClients();
            List<Client> clients = List.of(client);

            when(clientService.findAll())
                .thenReturn(clients);

            mockMvc.perform(get("/clients")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(clientService, times(1))
                .findAll();
        }

        @Test
        void shouldAllowFindAllWhenEmptyList() throws Exception {
            List<Client> clients = Collections.emptyList();

            when(clientService.findAll())
                .thenReturn(clients);

            mockMvc.perform(get("/clients")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

            verify(clientService, times(1))
                .findAll();
        }
    }

    @Nested
    class FindClient {

        @Test
        void shouldAllowFindClientByID() throws Exception {
            var client = ClientHelper.createClients();

            when(clientService.findById(any(String.class)))
                .thenReturn(client);

            mockMvc.perform(get("/clients/{id}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

            verify(clientService, times(1))
                .findById(any(String.class));
        }
    }

    @Nested
    class CreateClient {

        @Test
        void shouldAllowCreateNewClient() throws Exception {
            var clientRequest = ClientHelper.createClientRequest();

            doNothing().when(clientService).save(any(ClientRequest.class));

            mockMvc.perform(post("/clients")
                    .content(asJsonString(clientRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

            verify(clientService, times(1))
                .save(any(ClientRequest.class));
        }
    }

    @Nested
    class UpdateClient {

        @Test
        void shouldAllowUpdateClient() throws Exception {
            var clientRequest = ClientHelper.createClientRequest();
            var client = ClientHelper.createClients();

            when(clientService.update(anyString(), any(ClientRequest.class)))
                .thenReturn(client);

            mockMvc.perform(put("/clients/{id}", 1)
                    .content(asJsonString(clientRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

            verify(clientService, times(1))
                .update(anyString(), any(ClientRequest.class));
        }
    }

    @Nested
    class DeleteClient {

        @Test
        void shouldAllowDeleteClient() throws Exception {

            doNothing().when(clientService).deleteById(anyString());

            mockMvc.perform(delete("/clients/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

            verify(clientService, times(1))
                .deleteById(anyString());
        }
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
