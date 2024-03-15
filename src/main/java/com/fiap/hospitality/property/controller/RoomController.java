package com.fiap.hospitality.property.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.hospitality.property.entity.Room;
import com.fiap.hospitality.property.entity.dto.RoomRequest;
import com.fiap.hospitality.property.service.RoomService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/room")
@Tag(name = "Room", description = "Methods for manipulating Room's data")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "BAD REQUEST - Room error", content = @Content(examples = {
                @ExampleObject(summary = "Bad Request", value = "{\"statusCode\":400,\"message\":\"Bad Request\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "404", description = "NOT FOUND - Room Id not Found", content = @Content(examples = {
                @ExampleObject(summary = "Room ID not found", value = "{\"statusCode\":404,\"message\":\"Room ID not found\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR - Something went wrong", content = @Content(examples = {
                @ExampleObject(summary = "Internal Server Error", value = "{\"statusCode\":500,\"message\":\"Internal Server Error\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE))
})
public class RoomController {

    private final RoomService service;

    @Operation(summary = "Get all the Rooms", description = "Method for getting all the Rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - List of all Rooms", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class)), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        List<Room> rooms = new ArrayList<>();
        rooms.addAll(service.findAll());
        if (rooms.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @Operation(summary = "Get Room by ID", description = "Method to get a room based on the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Room.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable("id") String id) {
        Room room = service.findById(id);
        return ResponseEntity.ok().body(room);
    }

    @Operation(summary = "Save a Room", description = "Method to register a new Room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - Room successfully saved", content = @Content(schema = @Schema(implementation = Room.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping
    public ResponseEntity<Room> save(@RequestBody @Valid RoomRequest roomRequest) {
        Room room = service.save(roomRequest);
        return ResponseEntity.ok().body(room);
    }

    @Operation(summary = "Update a Room", description = "Method to update a Room")
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable String id,
            @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete a Room", description = "Method to delete a Room")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().body("Room Deleted with Success");
    }

    @Operation(summary = "Include a Room", description = "Method to include a new Room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - Rooms included with success", content = @Content(schema = @Schema(implementation = Room.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping("/addBathroom/{id}")
    public ResponseEntity<Room> includeBathroom(@PathVariable String id, String bathId) {
        Room property = service.includeBathroom(id, bathId);
        return ResponseEntity.ok().body(property);
    }

}
