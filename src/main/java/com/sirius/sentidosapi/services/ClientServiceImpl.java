package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.client.Client;
import com.sirius.sentidosapi.model.client.ClientEditingDTO;
import com.sirius.sentidosapi.model.client.ClientListingDTO;
import com.sirius.sentidosapi.model.user.User;
import com.sirius.sentidosapi.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<ClientListingDTO> getClients(Integer pageSize, Integer pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Client> paginatedClients = clientRepository.findAll(pageRequest);
        return new PageImpl<>(paginatedClients.getContent().stream()
                .map(ClientListingDTO::fromClient).collect(Collectors.toList()),
                pageRequest,
                paginatedClients.getTotalElements());
    }

    @Override
    public ClientListingDTO getClientById(String id) {
        Client client = clientRepository.findById(id).orElseThrow(()
            -> new IllegalArgumentException("Client not found"));
        return ClientListingDTO.fromClient(client);
    }

    @Override
    public ClientListingDTO save(ClientEditingDTO requestedClient) {
        Client client = new Client();

        client.setFirstName(requestedClient.getFirstName());
        client.setLastName(requestedClient.getLastName());
        client.setAddress((requestedClient.getAddress()));

        return ClientListingDTO.fromClient(clientRepository.save(client));
    }

    @Override
    public void updateClient(String id, ClientEditingDTO requestedClient) {
        Client clientFromDb = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found!"));

        clientFromDb.setFirstName(requestedClient.getFirstName());
        clientFromDb.setLastName(requestedClient.getLastName());
        clientFromDb.setAddress(requestedClient.getAddress());

        clientRepository.save(clientFromDb);
    }

    @Override
    public void deleteClient(String id) {
        clientRepository.deleteById(id);
    }
}
