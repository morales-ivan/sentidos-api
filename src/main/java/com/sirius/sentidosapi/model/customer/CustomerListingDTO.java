package com.sirius.sentidosapi.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomerListingDTO {
    String id;
    String customerNumber;
    String firstName;
    String lastName;
    String address;
    Set<String> mediaIds;

    public static CustomerListingDTO fromCustomer(Customer c) {
        Set<String> mediaIds = c.getMedia().stream().map(media -> media.getId()).collect(Collectors.toSet());
        return new CustomerListingDTO(c.getId(),
                c.getCustomerNumber(),
                c.getFirstName(),
                c.getLastName(),
                c.getAddress(),
                mediaIds);
    }
}
