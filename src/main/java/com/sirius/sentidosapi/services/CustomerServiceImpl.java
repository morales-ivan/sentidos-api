package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.customer.Customer;
import com.sirius.sentidosapi.model.customer.CustomerEditingDTO;
import com.sirius.sentidosapi.model.customer.CustomerListingDTO;
import com.sirius.sentidosapi.model.generators.CustomerNumberGenerator;
import com.sirius.sentidosapi.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    final CustomerRepository customerRepository;
    final CustomerNumberGenerator customerNumberGenerator;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerNumberGenerator customerNumberGenerator) {
        this.customerRepository = customerRepository;
        this.customerNumberGenerator = customerNumberGenerator;
    }

    @Override
    public Page<CustomerListingDTO> getCustomers(Integer pageSize, Integer pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Customer> paginatedCustomers = customerRepository.findAll(pageRequest);
        return new PageImpl<>(paginatedCustomers.getContent().stream()
                .map(CustomerListingDTO::fromCustomer).collect(Collectors.toList()),
                pageRequest,
                paginatedCustomers.getTotalElements());
    }

    @Override
    public CustomerListingDTO getCustomerById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
            -> new IllegalArgumentException("Customer not found!"));
        return CustomerListingDTO.fromCustomer(customer);
    }

    @Override
    public CustomerListingDTO save(CustomerEditingDTO requestedCustomer) {
        Customer customer = new Customer();

        customer.setCustomerNumber(customerNumberGenerator.generate());
        customer.setFirstName(requestedCustomer.getFirstName());
        customer.setLastName(requestedCustomer.getLastName());
        customer.setAddress((requestedCustomer.getAddress()));

        return CustomerListingDTO.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public void updateCustomer(String id, CustomerEditingDTO requestedCustomer) {
        Customer customerFromDb = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found!"));

        customerFromDb.setFirstName(requestedCustomer.getFirstName());
        customerFromDb.setLastName(requestedCustomer.getLastName());
        customerFromDb.setAddress(requestedCustomer.getAddress());

        customerRepository.save(customerFromDb);
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
