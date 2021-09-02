package com.sirius.sentidosapi.repositories;

import com.sirius.sentidosapi.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    Page<Customer> findAll(Pageable pageRequest);

    boolean existsByCustomerNumber(String customerNumber);

    @Query(value = "SELECT * FROM customers potentialCustomer WHERE ?1 IN (SELECT relation.media_id from customers_media relation where potentialCustomer.id=relation.customer_id);",
            nativeQuery = true)
    Customer findMediaOwner(String mediaId);
}