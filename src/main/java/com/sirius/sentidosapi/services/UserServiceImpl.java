package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.user.User;
import com.sirius.sentidosapi.model.user.UserEditingDTO;
import com.sirius.sentidosapi.model.user.UserListingDTO;

import com.sirius.sentidosapi.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserListingDTO> getUsers(Integer pageSize, Integer pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> paginatedUsers = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PageImpl<>(paginatedUsers.getContent().stream()
                .map(UserListingDTO::fromUser).collect(Collectors.toList()),
                pageRequest,
                paginatedUsers.getTotalElements());
    }

    @Override
    public UserListingDTO getUserById(String id) {
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
        user.setPassword(passwordEncoder.encode(requestedUser.getPassword()));
        user.setFirstName(requestedUser.getFirstName());
        user.setLastName(requestedUser.getLastName());
        user.setRole(requestedUser.getRole());

        return UserListingDTO.fromUser(userRepository.save(user));
    }

    @Override
    public void updateUser(String id, UserEditingDTO requestedUser) {

        User userFromDb = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        if (!Objects.equals(userFromDb.getUsername(), requestedUser.getUsername())) {
            if (userRepository.existsByUsername(requestedUser.getUsername())) {
                throw new IllegalArgumentException("Existing user name!");
            }
        }

        userFromDb.setEmail(requestedUser.getEmail());
        userFromDb.setUsername(requestedUser.getUsername());
        userFromDb.setPassword(passwordEncoder.encode(requestedUser.getPassword()));
        userFromDb.setFirstName(requestedUser.getFirstName());
        userFromDb.setLastName(requestedUser.getLastName());

        userRepository.save(userFromDb);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        format("User: %s, not found", username))
                );
    }
}
