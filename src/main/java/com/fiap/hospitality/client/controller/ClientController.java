package com.fiap.hospitality.client.controller;

import com.fiap.hospitality.client.entity.Client;
import com.fiap.hospitality.client.entity.dto.ClientRequest;
import com.fiap.hospitality.client.service.ClientService;
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
@RequestMapping("/clients")
@Tag(name = "Clients", description = "Methods for manipulating Client's data")
@RequiredArgsConstructor
@ApiResponses(value = {
    @ApiResponse(responseCode = "400", description = "BAD REQUEST - Client error", content = @Content(examples = {
        @ExampleObject(summary = "Bad Request", value = "{\"statusCode\":400,\"message\":\"Bad Request\"}")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "404", description = "NOT FOUND - Client Id not Found", content = @Content(examples = {
        @ExampleObject(summary = "Client ID not found", value = "{\"statusCode\":404,\"message\":\"Client ID not found\"}")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
    @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR - Something went wrong", content = @Content(examples = {
        @ExampleObject(summary = "Internal Server Error", value = "{\"statusCode\":500,\"message\":\"Internal Server Error\"}")
    }, mediaType = MediaType.APPLICATION_JSON_VALUE))
})
public class ClientController {

    private final ClientService service;

    @Operation(summary = "Get all the Clients", description = "Method for getting all the Clients")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "SUCCESS - List of all Clients", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Client.class)), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping
    public ResponseEntity<List<Client>> getAll() {
        List<Client> clients = new ArrayList<>();
        clients.addAll(service.findAll());
        if (clients.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @Operation(summary = "Save", description = "Method to register a new Client")
    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid ClientRequest clientRequest) {
        service.save(clientRequest);
        return new ResponseEntity<>("Client registered with success", HttpStatus.CREATED);
    }

}
