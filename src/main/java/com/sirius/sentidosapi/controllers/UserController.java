package com.sirius.sentidosapi.controllers;

import com.sirius.sentidosapi.model.user.UserEditingDTO;
import com.sirius.sentidosapi.model.user.UserListingDTO;
import com.sirius.sentidosapi.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserListingDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{pageNumber}")
    public Page<UserListingDTO> getPaginatedUsers(@RequestParam(required = false) Integer pageSize,
                                                  @PathVariable Integer pageNumber) {
        if (pageSize == null) pageSize = 5;
        pageNumber += 1;
        return userService.getPaginatedUsers(pageSize, pageNumber);
    }

    @GetMapping("/{userId}")
    public UserListingDTO getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserListingDTO saveUser(@RequestBody UserEditingDTO userEditingDto) {
        return userService.save(userEditingDto);
    }

    @PutMapping("/{userId}")
    public UserListingDTO updateUser(@PathVariable Long userId, @RequestBody UserEditingDTO userEditingDto) {
        userService.updateUser(userId, userEditingDto);
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserListingDTO> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
