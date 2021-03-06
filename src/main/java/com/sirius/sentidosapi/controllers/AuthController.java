package com.sirius.sentidosapi.controllers;

import com.sirius.sentidosapi.config.security.JWT;
import com.sirius.sentidosapi.config.security.JwtTokenUtil;
import com.sirius.sentidosapi.model.user.User;
import com.sirius.sentidosapi.model.user.UserAuthenticationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<JWT> login(@RequestBody UserAuthenticationDTO requestedUser) {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    requestedUser.getUsername(), requestedUser.getPassword()
                            )
                    );

            User user = (User) authenticate.getPrincipal();

            return ResponseEntity.ok().body(new JWT(jwtTokenUtil.generateAccessToken(user)));
//                    .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
//                    .body(UserListingDTO.fromUser(user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
