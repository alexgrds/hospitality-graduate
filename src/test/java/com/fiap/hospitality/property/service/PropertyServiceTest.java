package com.fiap.hospitality.property.service;


import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRequest;
import com.fiap.hospitality.property.repository.PropertyRepository;
import com.fiap.hospitality.utils.PropertyHelper;
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

public class PropertyServiceTest {

    private PropertyService propertyService;

    @Mock
    private RoomService roomService;

    @Mock
    private PropertyRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        propertyService = new PropertyService(repository, roomService);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class findPropertys {

        @Test
        void shouldFindAllPropertys() {

            Property property = new Property();
            List<Property> propertys = List.of(property);

            when(repository.findAll())
                .thenReturn(propertys);

            var propertyList = propertyService.findAll();

            assertThat(propertyList).hasSize(1);

            verify(repository, times(1)).findAll();

        }

        @Test
        void shouldFindById() {

            Property property = new Property();
            property.setId("1");

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(property));

            var PropertyReturn = propertyService.findById(property.getId());

            verify(repository, times(1))
                .findById(property.getId());
            assertThat(PropertyReturn.getId())
                .isEqualTo(property.getId());

        }
    }

    @Nested
    class createProperty {

        @Test
        void shouldCreateProperty() {

            Property property = new Property();
            property.setPropertyName("Hotel");

            PropertyAddressRequest propertyRequest = PropertyHelper.createPropertyAddressRequest();

            when(repository.save(any(Property.class)))
                .thenReturn(property);

            propertyService.save(propertyRequest);

            verify(repository, times(1))
                .save(property);
        }
    }

    @Nested
    class updateProperty {

        @Test
        void shouldUpdateProperty() {

            Property property = new Property();
            property.setId("1");
            property.setPropertyName("Hotel");
            PropertyAddressRequest propertyRequest = PropertyHelper.createPropertyAddressRequest();

            when(repository.save(any(Property.class)))
                .thenReturn(property);

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(property));

            Property propertySaved = propertyService.update(property.getId(), propertyRequest);

            assertThat(propertySaved)
                .isNotNull();
            assertThat(propertySaved.getPropertyName())
                .isEqualTo(propertyRequest.propertyName());

            verify(repository, times(1))
                .save(property);
        }
    }

    @Nested
    class deleteProperty {

        @Test
        void shouldDeleteProperty() {

            Property property = new Property();
            property.setId("1");
            property.setPropertyName("Hotel");

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(property));

            doNothing().when(repository).deleteById(anyString());

            propertyService.deleteById(property.getId());

            verify(repository, times(1))
                .deleteById(property.getId());
        }
    }
}
