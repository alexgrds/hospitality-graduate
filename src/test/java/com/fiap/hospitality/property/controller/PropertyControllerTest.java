package com.fiap.hospitality.property.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRequest;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRoomResponse;
import com.fiap.hospitality.property.service.PropertyService;
import com.fiap.hospitality.utils.PropertyHelper;
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

public class PropertyControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PropertyService propertyService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PropertyController propertyController = new PropertyController(propertyService);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController)
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
    class ListPropertys {

        @Test
        void shouldListPropertys() throws Exception {
            var property = PropertyHelper.createPropertyAddressRoomResponse();
            List<PropertyAddressRoomResponse> propertys = List.of(property);

            when(propertyService.findAll())
                .thenReturn(propertys);

            mockMvc.perform(get("/property")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(propertyService, times(1))
                .findAll();
        }

        @Test
        void shouldAllowFindAllWhenEmptyList() throws Exception {
            List<PropertyAddressRoomResponse> propertys = Collections.emptyList();

            when(propertyService.findAll())
                .thenReturn(propertys);

            mockMvc.perform(get("/property")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(propertyService, times(1))
                .findAll();
        }
    }

    @Nested
    class FindProperty {

        @Test
        void shouldAllowFindPropertyByID() throws Exception {
            Property property = new Property();
            property.setId("1");

            when(propertyService.findById(any(String.class)))
                .thenReturn(property);

            mockMvc.perform(get("/property/{id}", property.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

            verify(propertyService, times(1))
                .findById(any(String.class));
        }
    }

    @Nested
    class CreateProperty {

        @Test
        void shouldAllowCreateNewProperty() throws Exception {
            var propertyRequest = PropertyHelper.createPropertyAddressRequest();
            Property property = new Property();
            property.setId("1");

            when(propertyService.save(any(PropertyAddressRequest.class)))
                .thenReturn(property);

            mockMvc.perform(post("/property")
                    .content(asJsonString(propertyRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(propertyService, times(1))
                .save(any(PropertyAddressRequest.class));
        }
    }

    @Nested
    class UpdateProperty {

        @Test
        void shouldAllowUpdateProperty() throws Exception {
            var propertyRequest = PropertyHelper.createPropertyAddressRequest();
            Property property = new Property();
            property.setId("1");

            when(propertyService.update(anyString(), any(PropertyAddressRequest.class)))
                .thenReturn(property);

            mockMvc.perform(put("/property/{id}", 1)
                    .content(asJsonString(propertyRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

            verify(propertyService, times(1))
                .update(anyString(), any(PropertyAddressRequest.class));
        }
    }

    @Nested
    class DeleteProperty {

        @Test
        void shouldAllowDeleteProperty() throws Exception {

            doNothing().when(propertyService).deleteById(anyString());

            mockMvc.perform(delete("/property/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

            verify(propertyService, times(1))
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
