package com.sirius.sentidosapi.model.generators;

import com.sirius.sentidosapi.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerNumberGenerator {
    private final CustomerRepository customerRepository;
    private final RandomString generator = new RandomString(12);

    public CustomerNumberGenerator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String generate() {
        String customerNumber = generator.nextString();

        while (customerRepository.existsByCustomerNumber(customerNumber))
            customerNumber = generator.nextString();

        return customerNumber;
    }
}
