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

import com.fiap.hospitality.property.entity.Bathroom;
import com.fiap.hospitality.property.entity.dto.BathroomRequest;
import com.fiap.hospitality.property.service.BathroomService;

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
@RequestMapping("/bathroom")
@Tag(name = "Bathroom", description = "Methods for manipulating Bathroom's data")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "BAD REQUEST - Bathroom error", content = @Content(examples = {
                @ExampleObject(summary = "Bad Request", value = "{\"statusCode\":400,\"message\":\"Bad Request\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "404", description = "NOT FOUND - Bathroom Id not Found", content = @Content(examples = {
                @ExampleObject(summary = "Bathroom ID not found", value = "{\"statusCode\":404,\"message\":\"Bathroom ID not found\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR - Something went wrong", content = @Content(examples = {
                @ExampleObject(summary = "Internal Server Error", value = "{\"statusCode\":500,\"message\":\"Internal Server Error\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE))
})
public class BathroomController {

    private final BathroomService service;

    @Operation(summary = "Get all the Bathrooms", description = "Method for getting all the Bathrooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - List of all Bathrooms", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Bathroom.class)), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping
    public ResponseEntity<List<Bathroom>> getAll() {
        List<Bathroom> rooms = new ArrayList<>();
        rooms.addAll(service.findAll());
        if (rooms.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @Operation(summary = "Get Bathroom by ID", description = "Method to get a bathroom based on the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Bathroom.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Bathroom> getById(@PathVariable("id") String id) {
        Bathroom bathroom = service.findById(id);
        return ResponseEntity.ok().body(bathroom);
    }

    @Operation(summary = "Save a Bathroom", description = "Method to register a new Bathroom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - Bathroom successfully saved", content = @Content(schema = @Schema(implementation = Bathroom.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping
    public ResponseEntity<Bathroom> save(@RequestBody @Valid BathroomRequest roomRequest) {
        Bathroom bathroom = service.save(roomRequest);
        return ResponseEntity.ok().body(bathroom);
    }

    @Operation(summary = "Update a Bathroom", description = "Method to update a Bathroom")
    @PutMapping("/{id}")
    public ResponseEntity<Bathroom> updateBathroom(@PathVariable String id,
            @Valid @RequestBody BathroomRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete a Bathroom", description = "Method to delete a Bathroom")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBathroom(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().body("Bathroom Deleted with Success");
    }

}
