package com.sirius.sentidosapi.controllers;

import com.sirius.sentidosapi.model.customer.CustomerEditingDTO;
import com.sirius.sentidosapi.model.customer.CustomerListingDTO;
import com.sirius.sentidosapi.services.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/get-all/{pageNumber}")
    public Page<CustomerListingDTO> getCustomers(@RequestParam(required = false) Integer pageSize,
                                                 @PathVariable Integer pageNumber) {
        if (pageSize == null) pageSize = 10;
        return customerService.getCustomers(pageSize, pageNumber);
    }

    @GetMapping("/{customerId}")
    public CustomerListingDTO getCustomer(@PathVariable String customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public CustomerListingDTO saveCustomer(@RequestBody CustomerEditingDTO customerEditingDto) {
        return customerService.save(customerEditingDto);
    }

    @PutMapping("/{customerId}")
    public CustomerListingDTO updateCustomer(@PathVariable String customerId,
                                             @RequestBody CustomerEditingDTO customerEditingDto) {
        customerService.updateCustomer(customerId, customerEditingDto);
        return customerService.getCustomerById(customerId);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerListingDTO> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

