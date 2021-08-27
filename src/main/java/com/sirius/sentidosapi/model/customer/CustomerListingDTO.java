package com.sirius.sentidosapi.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerListingDTO {
    String id;
    String customerNumber;
    String firstName;
    String lastName;
    String address;

    public static CustomerListingDTO fromCustomer(Customer c) {
        return new CustomerListingDTO(c.getId(), c.getCustomerNumber(), c.getFirstName(), c.getLastName(), c.getAddress());
    }
}
