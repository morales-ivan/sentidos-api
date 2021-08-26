package com.sirius.sentidosapi.controllers;

import com.sirius.sentidosapi.model.client.ClientEditingDTO;
import com.sirius.sentidosapi.model.client.ClientListingDTO;
import com.sirius.sentidosapi.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{pageNumber}")
    public Page<ClientListingDTO> getClients(@RequestParam(required = false) Integer pageSize,
                                             @PathVariable Integer pageNumber) {
        if (pageSize == null) pageSize = 10;
        pageNumber -= 1;
        return clientService.getClients(pageSize, pageNumber);
    }

    @GetMapping("/id/{clientId}")
    public ClientListingDTO getClient(@PathVariable String clientId) {
        return clientService.getClientById(clientId);
    }

    @PostMapping
    public ClientListingDTO saveClient(@RequestBody ClientEditingDTO clientEditingDto) {
        return clientService.save(clientEditingDto);
    }

    @PutMapping("/{clientId}")
    public ClientListingDTO updateClient(@PathVariable String clientId, @RequestBody ClientEditingDTO clientEditingDto) {
        clientService.updateClient(clientId, clientEditingDto);
        return clientService.getClientById(clientId);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<ClientListingDTO> deleteClient(@PathVariable String clientId) {
        clientService.deleteClient(clientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

