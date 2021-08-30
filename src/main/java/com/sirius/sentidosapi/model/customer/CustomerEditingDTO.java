package com.sirius.sentidosapi.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerEditingDTO {
    String firstName;
    String lastName;
    String address;
}
