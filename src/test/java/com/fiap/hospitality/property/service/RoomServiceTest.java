package com.fiap.hospitality.property.service;


import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.dto.RoomRequest;
import com.fiap.hospitality.property.repository.RoomRepository;
import com.fiap.hospitality.utils.RoomHelper;
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


public class RoomServiceTest {

    private RoomService roomService;
    private BathroomService bathroomService;

    @Mock
    private RoomRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        roomService = new RoomService(repository, bathroomService);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class findRooms {

        @Test
        void shouldFindAllRooms() {

            List<Room> rooms = List.of(new Room());
            rooms.get(0).setId("1");

            when(repository.findAll())
                .thenReturn(rooms);

            var roomList = roomService.findAll();

            assertThat(roomList).hasSize(1);

            verify(repository, times(1)).findAll();

        }

        @Test
        void shouldFindById() {

            Room room = new Room();
            room.setId("1");

            when(repository.findById(anyString()))
                .thenReturn(Optional.ofNullable(room));

            var roomReturn = roomService.findById(room.getId());

            verify(repository, times(1))
                .findById(room.getId());
            assertThat(roomReturn.getId())
                .isEqualTo(room.getId());

        }
    }

    @Nested
    class createRoom {

        @Test
        void shouldCreateRoom() {

            Room room = new Room();
            room.setId("1");
            room.setId(null);
            RoomRequest roomRequest = RoomHelper.createRoomRequest();

            when(repository.save(any(Room.class)))
                .thenReturn(room);

            roomService.save(roomRequest);

            verify(repository, times(1))
                .save(room);
        }
    }

    @Nested
    class updateRoom {

        @Test
        void shouldUpdateRoom() {

            Room room = new Room();
            room.setId("1");
            RoomRequest roomRequest = RoomHelper.createRoomRequest();

            when(repository.save(any(Room.class)))
                .thenReturn(room);

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(room));

            Room roomSaved = roomService.update(room.getId(), roomRequest);

            assertThat(roomSaved)
                .isNotNull();
            assertThat(roomSaved.getDescription())
                .isEqualTo(roomRequest.description());

            verify(repository, times(1))
                .save(room);
        }
    }

    @Nested
    class deleteRoom {

        @Test
        void shouldDeleteRoom() {

            Room room = new Room();
            room.setId("1");

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(room));

            doNothing().when(repository).deleteById(anyString());

            roomService.deleteById(room.getId());

            verify(repository, times(1))
                .deleteById(room.getId());
        }
    }
}
