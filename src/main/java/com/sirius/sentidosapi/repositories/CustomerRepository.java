package com.sirius.sentidosapi.repositories;

import com.sirius.sentidosapi.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    Page<Customer> findAll(Pageable pageRequest);

    boolean existsByCustomerNumber(String customerNumber);
}