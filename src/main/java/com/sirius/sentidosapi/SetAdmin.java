package com.sirius.sentidosapi;

import com.sirius.sentidosapi.model.user.Role;
import com.sirius.sentidosapi.model.user.UserEditingDTO;
import com.sirius.sentidosapi.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SetAdmin implements ApplicationRunner {
    @Value("${admin_username}")
    String adminUsername;
    @Value("${admin_password}")
    String adminPassword;

    final UserService userService;

    public SetAdmin(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!userService.existsByUsername(adminUsername)) {
            log.info("Admin user not found, creating user...");
            UserEditingDTO adminUser = new UserEditingDTO();

            adminUser.setUsername(adminUsername);
            adminUser.setPassword(adminPassword);
            adminUser.setRole(Role.ADMIN);

            userService.save(adminUser);
            log.info("Admin user created");
        } else log.info("ADMIN user found, avoiding creation.");
    }
}
