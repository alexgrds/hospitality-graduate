package com.fiap.hospitality.client.controller;

import com.fiap.hospitality.client.entity.Client;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
