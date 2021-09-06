package com.sirius.sentidosapi.model.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserAuthenticationDTO {
    @NotNull
    String username;
    @NotNull
    String password;
}
