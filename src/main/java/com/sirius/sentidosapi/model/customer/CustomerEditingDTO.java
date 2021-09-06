package com.sirius.sentidosapi.model.customer;

import com.sirius.sentidosapi.model.media.Media;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class CustomerEditingDTO {
    String firstName;
    String lastName;
    String address;
    Set<String> mediaIds;
}
