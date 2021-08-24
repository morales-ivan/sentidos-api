package com.sirius.sentidosapi.repositories;

import com.sirius.sentidosapi.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Page<User> findAll(Pageable pageRequest);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
