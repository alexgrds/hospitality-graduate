package com.fiap.hospitality.property.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import com.fiap.hospitality.property.entity.Property;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRequest;
import com.fiap.hospitality.property.entity.dto.PropertyAddressRoomResponse;
import com.fiap.hospitality.property.service.PropertyService;

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
@RequestMapping("/property")
@Tag(name = "Property", description = "Methods for manipulating Property's data")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "BAD REQUEST - Property error", content = @Content(examples = {
                @ExampleObject(summary = "Bad Request", value = "{\"statusCode\":400,\"message\":\"Bad Request\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "404", description = "NOT FOUND - Property Id not Found", content = @Content(examples = {
                @ExampleObject(summary = "Property ID not found", value = "{\"statusCode\":404,\"message\":\"Property ID not found\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR - Something went wrong", content = @Content(examples = {
                @ExampleObject(summary = "Internal Server Error", value = "{\"statusCode\":500,\"message\":\"Internal Server Error\"}")
        }, mediaType = MediaType.APPLICATION_JSON_VALUE))
})
public class PropertyController {

    private final PropertyService service;

    @Operation(summary = "Get all the Properties", description = "Method for getting all the Properties")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - List of all Properties", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Property.class)), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping
    public ResponseEntity<List<Property>> getAll() {
        List<Property> properties = new ArrayList<>();
        properties.addAll(service.findAll());
        if (properties.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @Operation(summary = "Get Property by ID", description = "Method to get a property based on the ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Property.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Property> getById(@PathVariable("id") String id) {
        Property property = service.findById(id);
        return ResponseEntity.ok().body(property);
    }

    @Operation(summary = "Save a Property", description = "Method to register a new Property")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - Property successfully saved", content = @Content(schema = @Schema(implementation = Property.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping
    public ResponseEntity<Property> save(@RequestBody @Valid PropertyAddressRequest propertyRequest) {
        Property property = service.save(propertyRequest);
        return ResponseEntity.ok().body(property);
    }

    @Operation(summary = "Update a Property", description = "Method to update a Property")
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable String id,
            @Valid @RequestBody PropertyAddressRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete a Property", description = "Method to delete a Property")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().body("Property Deleted with Success");
    }

    @Operation(summary = "Include a Room", description = "Method to include a new Room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS - Rooms included with success", content = @Content(schema = @Schema(implementation = PropertyAddressRoomResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @PostMapping("/addRoom/{id}")
    public ResponseEntity<PropertyAddressRoomResponse> includeRooms(@PathVariable String id, @Valid @RequestBody Set<String> roomRequest) {
        PropertyAddressRoomResponse property = service.includeRooms(id, roomRequest);
        return ResponseEntity.ok().body(property);
    }

}
