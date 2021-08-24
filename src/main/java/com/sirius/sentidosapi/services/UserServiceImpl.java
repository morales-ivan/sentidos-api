package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.user.User;
import com.sirius.sentidosapi.model.user.UserEditingDTO;
import com.sirius.sentidosapi.model.user.UserListingDTO;

import com.sirius.sentidosapi.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserListingDTO> getUsers() {
        List<User> users = new ArrayList<>((Collection<? extends User>) userRepository.findAll());
        return users.stream().map(UserListingDTO::fromUser).collect(Collectors.toList());
    }

    @Override
    public Page<UserListingDTO> getPaginatedUsers(Integer pageSize, Integer pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> paginatedUsers = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PageImpl<>(paginatedUsers.getContent().stream()
                .map(UserListingDTO::fromUser).collect(Collectors.toList()),
                pageRequest,
                paginatedUsers.getTotalElements());
    }

    @Override
    public UserListingDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("User not found"));
        return UserListingDTO.fromUser(user);
    }

    @Override
    public UserListingDTO save(UserEditingDTO requestedUser) {
        if (userRepository.existsByUsername(requestedUser.getUsername()))
            throw new IllegalArgumentException("Existing username!");

        User user = new User();

        user.setEmail(requestedUser.getEmail());
        user.setUsername(requestedUser.getUsername());
        user.setPassword(requestedUser.getPassword());
        // user.setPassword(passwordEncoder.encode(requestUser.getPassword()));
        user.setFirstName(requestedUser.getFirstName());
        user.setLastName(requestedUser.getLastName());

        return UserListingDTO.fromUser(userRepository.save(user));
    }

    @Override
    public void updateUser(Long id, UserEditingDTO requestedUser) {
        if (userRepository.existsByUsername(requestedUser.getUsername())) {
            throw new IllegalArgumentException("Existing user name!");
        }

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        userFromDb.setEmail(requestedUser.getEmail());
        userFromDb.setUsername(requestedUser.getUsername());
        userFromDb.setPassword(requestedUser.getPassword());
        // userFromDb.setPassword(passwordEncoder.encode(requestedUser.getPassword()));
        userFromDb.setFirstName(requestedUser.getFirstName());
        userFromDb.setLastName(requestedUser.getLastName());

        userRepository.save(userFromDb);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
