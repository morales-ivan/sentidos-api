package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.user.UserEditingDTO;
import com.sirius.sentidosapi.model.user.UserListingDTO;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserListingDTO> getUsers(Integer pageSize, Integer pageNumber);

    UserListingDTO getUserById(String id);

    UserListingDTO save(UserEditingDTO requestedUser);

    void updateUser(String id, UserEditingDTO requestedUser);

    void deleteUser(String id);

    boolean existsByUsername(String username);
}
