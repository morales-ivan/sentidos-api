package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.client.ClientEditingDTO;
import com.sirius.sentidosapi.model.client.ClientListingDTO;
import org.springframework.data.domain.Page;

public interface ClientService {
    Page<ClientListingDTO> getClients(Integer pageSize, Integer pageNumber);

    ClientListingDTO getClientById(String id);

    ClientListingDTO save(ClientEditingDTO requestedClient);

    void updateClient(String id, ClientEditingDTO requestedClient);

    void deleteClient(String id);
}
