package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    User loadUserByUsername(String username)
            throws UsernameNotFoundException;
}
