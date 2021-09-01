package com.sirius.sentidosapi.model.user;

import lombok.Data;

@Data
public class UserEditingDTO {
    String email;
    String username;
    String password;
    String firstName;
    String lastName;
    Role role;
}
