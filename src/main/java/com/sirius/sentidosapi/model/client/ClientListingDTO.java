package com.sirius.sentidosapi.model.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientListingDTO {
    String id;
    String firstName;
    String lastName;
    String address;

    public static ClientListingDTO fromClient(Client c) {
        return new ClientListingDTO(c.getId(), c.getFirstName(), c.getLastName(), c.getAddress());
    }
}
