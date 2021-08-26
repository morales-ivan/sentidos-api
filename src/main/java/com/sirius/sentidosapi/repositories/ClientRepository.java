package com.sirius.sentidosapi.repositories;

import com.sirius.sentidosapi.model.client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, String> {
    Page<Client> findAll(Pageable pageRequest);
}