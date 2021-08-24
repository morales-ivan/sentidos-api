package com.sirius.sentidosapi.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserListingDTO {
    Long id;
    String email;
    String username;
    String firstName;
    String lastName;

    public static UserListingDTO fromUser(User u) {
        return new UserListingDTO(u.getId(), u.getEmail(), u.getUsername(), u.getFirstName(), u.getLastName());
    }
}
