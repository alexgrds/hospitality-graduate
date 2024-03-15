package com.fiap.hospitality.property.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.dto.RoomRequest;
import com.fiap.hospitality.property.service.RoomService;
import com.fiap.hospitality.utils.RoomHelper;
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

public class RoomControllerTest {
    private MockMvc mockMvc;

    @Mock
    private RoomService roomService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        RoomController roomController = new RoomController(roomService);
        mockMvc = MockMvcBuilders.standaloneSetup(roomController)
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
    class ListRooms {

        @Test
        void shouldListRooms() throws Exception {
            Room room = new Room();
            List<Room> Rooms = List.of(room);

            when(roomService.findAll())
                .thenReturn(Rooms);

            mockMvc.perform(get("/room")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(roomService, times(1))
                .findAll();
        }

        @Test
        void shouldAllowFindAllWhenEmptyList() throws Exception {
            List<Room> rooms = Collections.emptyList();

            when(roomService.findAll())
                .thenReturn(rooms);

            mockMvc.perform(get("/room")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

            verify(roomService, times(1))
                .findAll();
        }
    }

    @Nested
    class FindRoom {

        @Test
        void shouldAllowFindRoomByID() throws Exception {
            Room room = new Room();
            room.setId("1");

            when(roomService.findById(any(String.class)))
                .thenReturn(room);

            mockMvc.perform(get("/room/{id}", room.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

            verify(roomService, times(1))
                .findById(any(String.class));
        }
    }

    @Nested
    class CreateRoom {

        @Test
        void shouldAllowCreateNewRoom() throws Exception {
            var roomRequest = RoomHelper.createRoomRequest();
            Room room = new Room();
            room.setId("1");

            when(roomService.save(any(RoomRequest.class)))
                .thenReturn(room);

            mockMvc.perform(post("/room")
                    .content(asJsonString(roomRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(roomService, times(1))
                .save(any(RoomRequest.class));
        }
    }

    @Nested
    class UpdateRoom {

        @Test
        void shouldAllowUpdateRoom() throws Exception {
            var roomRequest = RoomHelper.createRoomRequest();
            Room room = new Room();
            room.setId("1");

            when(roomService.update(anyString(), any(RoomRequest.class)))
                .thenReturn(room);

            mockMvc.perform(put("/room/{id}", 1)
                    .content(asJsonString(roomRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

            verify(roomService, times(1))
                .update(anyString(), any(RoomRequest.class));
        }
    }

    @Nested
    class DeleteRoom {

        @Test
        void shouldAllowDeleteRoom() throws Exception {

            doNothing().when(roomService).deleteById(anyString());

            mockMvc.perform(delete("/room/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

            verify(roomService, times(1))
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
