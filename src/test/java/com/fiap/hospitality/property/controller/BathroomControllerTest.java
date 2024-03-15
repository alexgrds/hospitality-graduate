package com.fiap.hospitality.property.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.hospitality.property.entity.Bathroom;
import com.fiap.hospitality.property.entity.dto.BathroomRequest;
import com.fiap.hospitality.property.service.BathroomService;
import com.fiap.hospitality.utils.BathroomHelper;
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

public class BathroomControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BathroomService bathroomService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        BathroomController BathroomController = new BathroomController(bathroomService);
        mockMvc = MockMvcBuilders.standaloneSetup(BathroomController)
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
    class ListBathrooms {

        @Test
        void shouldListBathrooms() throws Exception {
            var bathroom = BathroomHelper.createBathrooms();
            List<Bathroom> Bathrooms = List.of(bathroom);

            when(bathroomService.findAll())
                .thenReturn(Bathrooms);

            mockMvc.perform(get("/bathroom")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(bathroomService, times(1))
                .findAll();
        }

        @Test
        void shouldAllowFindAllWhenEmptyList() throws Exception {
            List<Bathroom> bathrooms = Collections.emptyList();

            when(bathroomService.findAll())
                .thenReturn(bathrooms);

            mockMvc.perform(get("/bathroom")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

            verify(bathroomService, times(1))
                .findAll();
        }
    }

    @Nested
    class FindBathroom {

        @Test
        void shouldAllowFindBathroomByID() throws Exception {
            var bathroom = BathroomHelper.createBathrooms();

            when(bathroomService.findById(any(String.class)))
                .thenReturn(bathroom);

            mockMvc.perform(get("/bathroom/{id}", bathroom.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

            verify(bathroomService, times(1))
                .findById(any(String.class));
        }
    }

    @Nested
    class CreateBathroom {

        @Test
        void shouldAllowCreateNewBathroom() throws Exception {
            var bathroomRequest = BathroomHelper.createBathroomRequest();
            var bathroom = BathroomHelper.createBathrooms();

            when(bathroomService.save(any(BathroomRequest.class)))
                .thenReturn(bathroom);

            mockMvc.perform(post("/bathroom")
                    .content(asJsonString(bathroomRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(bathroomService, times(1))
                .save(any(BathroomRequest.class));
        }
    }

    @Nested
    class UpdateBathroom {

        @Test
        void shouldAllowUpdateBathroom() throws Exception {
            var bathroomRequest = BathroomHelper.createBathroomRequest();
            var bathroom = BathroomHelper.createBathrooms();

            when(bathroomService.update(anyString(), any(BathroomRequest.class)))
                .thenReturn(bathroom);

            mockMvc.perform(put("/bathroom/{id}", 1)
                    .content(asJsonString(bathroomRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

            verify(bathroomService, times(1))
                .update(anyString(), any(BathroomRequest.class));
        }
    }

    @Nested
    class DeleteBathroom {

        @Test
        void shouldAllowDeleteBathroom() throws Exception {

            doNothing().when(bathroomService).deleteById(anyString());

            mockMvc.perform(delete("/bathroom/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

            verify(bathroomService, times(1))
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
