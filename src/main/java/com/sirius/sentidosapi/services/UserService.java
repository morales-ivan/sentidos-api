package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.user.UserEditingDTO;
import com.sirius.sentidosapi.model.user.UserListingDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<UserListingDTO> getUsers();

    Page<UserListingDTO> getPaginatedUsers(Integer pageSize, Integer pageNumber);

    UserListingDTO getUserById(Long id);

    UserListingDTO save(UserEditingDTO requestedUser);

    void updateUser(Long id, UserEditingDTO requestedUser);

    void deleteUser(Long id);
}
