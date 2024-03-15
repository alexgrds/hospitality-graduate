package com.fiap.hospitality.property.service;


import com.fiap.hospitality.property.entity.Bathroom;
import com.fiap.hospitality.property.entity.dto.BathroomRequest;
import com.fiap.hospitality.property.repository.BathroomRepository;
import com.fiap.hospitality.utils.BathroomHelper;
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


public class BathroomServiceTest {

    private BathroomService bathroomService;

    @Mock
    private BathroomRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        bathroomService = new BathroomService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class findBathrooms {

        @Test
        void shouldFindAllBathrooms() {

            List<Bathroom> bathrooms = List.of(BathroomHelper.createBathrooms());

            when(repository.findAll())
                .thenReturn(bathrooms);

            var bathroomList = bathroomService.findAll();

            assertThat(bathroomList).hasSize(1);

            verify(repository, times(1)).findAll();

        }

        @Test
        void shouldFindById() {

            Bathroom bathroom = BathroomHelper.createBathrooms();

            when(repository.findById(anyString()))
                .thenReturn(Optional.ofNullable(bathroom));

            var BathroomReturn = bathroomService.findById(bathroom.getId());

            verify(repository, times(1))
                .findById(bathroom.getId());
            assertThat(BathroomReturn.getId())
                .isEqualTo(bathroom.getId());

        }
    }

    @Nested
    class createBathroom {

        @Test
        void shouldCreateBathroom() {

            Bathroom bathroom = BathroomHelper.createBathrooms();
            bathroom.setId(null);
            BathroomRequest bathroomRequest = BathroomHelper.createBathroomRequest();

            when(repository.save(any(Bathroom.class)))
                .thenReturn(bathroom);

            bathroomService.save(bathroomRequest);

            verify(repository, times(1))
                .save(bathroom);
        }
    }

    @Nested
    class updateBathroom {

        @Test
        void shouldUpdateBathroom() {

            Bathroom bathroom = BathroomHelper.createBathrooms();
            BathroomRequest bathroomRequest = BathroomHelper.createBathroomRequest();

            when(repository.save(any(Bathroom.class)))
                .thenReturn(bathroom);

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(bathroom));

            Bathroom bathroomSaved = bathroomService.update(bathroom.getId(), bathroomRequest);

            assertThat(bathroomSaved)
                .isNotNull();
            assertThat(bathroomSaved.getDescription())
                .isEqualTo(bathroomRequest.description());

            verify(repository, times(1))
                .save(bathroom);
        }
    }

    @Nested
    class deleteBathroom {

        @Test
        void shouldDeleteBathroom() {

            Bathroom bathroom = BathroomHelper.createBathrooms();

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(bathroom));

            doNothing().when(repository).deleteById(anyString());

            bathroomService.deleteById(bathroom.getId());

            verify(repository, times(1))
                .deleteById(bathroom.getId());
        }
    }
}
