package com.fiap.hospitality.option.controller;

import com.fiap.hospitality.option.entity.Option;
import com.fiap.hospitality.option.entity.dto.OptionRequest;
import com.fiap.hospitality.option.service.OptionService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/options")
@Tag(name = "Options", description = "Methods for manipulating Options")
@RequiredArgsConstructor
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "BAD REQUEST - Option error", content = @Content(examples = {
        @ExampleObject(summary = "Bad Request", value = "{\"statusCode\":400,\"message\":\"Bad Request\"}")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "404", description = "NOT FOUND - Option Id not Found", content = @Content(examples = {
        @ExampleObject(summary = "Option ID not found", value = "{\"statusCode\":404,\"message\":\"Option ID not found\"}")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR - Something went wrong", content = @Content(examples = {
        @ExampleObject(summary = "Internal Server Error", value = "{\"statusCode\":500,\"message\":\"Internal Server Error\"}")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
})
public class OptionController {

    private final OptionService service;

    @Operation(summary = "Get all the Options", description = "Method for getting all the Options")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SUCCESS - List of all Options", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Option.class)), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping
    public ResponseEntity<List<Option>> getAll() {
        List<Option> options = new ArrayList<>();
        options.addAll(service.findAll());
        if (options.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @Operation(summary = "Get Option by ID", description = "Method to get a Option based on the ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = Option.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Option> getById(@PathVariable("id") String id) {
        Option option = service.findById(id);
        return ResponseEntity.ok().body(option);
    }

    @Operation(summary = "Save", description = "Method to register a new Option")
    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid OptionRequest optionRequest) {
        service.save(optionRequest);
        return new ResponseEntity<>("Option registered with success", HttpStatus.CREATED);
    }

    @Operation(summary = "Update a Option", description = "Method to update a Option")
    @PutMapping("/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable String id, @Valid @RequestBody OptionRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete a Option", description = "Method to delete a Option")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteServiceItem(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().body("Option Deleted with Success");
    }

}
