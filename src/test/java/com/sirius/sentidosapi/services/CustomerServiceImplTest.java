package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.customer.Customer;
import com.sirius.sentidosapi.model.customer.CustomerEditingDTO;
import com.sirius.sentidosapi.model.customer.CustomerListingDTO;
import com.sirius.sentidosapi.model.generators.CustomerNumberGenerator;
import com.sirius.sentidosapi.repositories.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CustomerServiceImplTest {
    final private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    final private CustomerNumberGenerator customerNumberGenerator = Mockito.mock(CustomerNumberGenerator.class);
    private CustomerService customerService = null;

    @BeforeEach
    void setup() {
        this.customerService = new CustomerServiceImpl(customerRepository, customerNumberGenerator, mediaRepository);
    }

    @Test
    @DisplayName("Test should pass when the method returns the ListingDTO with the data for the specified id")
    void returnListingDtoWhenFindingById() {
        Customer customer = new Customer("asd9sa87f",
                "ASH87SA9A47J",
                "Ivan",
                "Morales",
                "Calle Falsa 123");

        Mockito.when(customerRepository.findById("asd9sa87f")).thenReturn(Optional.of(customer));

        CustomerListingDTO response = customerService.getCustomerById("asd9sa87f");

        assertAll("Customer data",
                () -> assertEquals(response.getId(), customer.getId()),
                () -> assertEquals(response.getCustomerNumber(), customer.getCustomerNumber()),
                () -> assertEquals(response.getFirstName(), customer.getFirstName()),
                () -> assertEquals(response.getLastName(), customer.getLastName()),
                () -> assertEquals(response.getAddress(), customer.getAddress())
        );

//        Assertions.assertThat(response.getId()).isEqualTo(customer.getId());
//        Assertions.assertThat(response.getCustomerNumber()).isEqualTo(customer.getCustomerNumber());
//        Assertions.assertThat(response.getFirstName()).isEqualTo(customer.getFirstName());
//        Assertions.assertThat(response.getLastName()).isEqualTo(customer.getLastName());
//        Assertions.assertThat(response.getAddress()).isEqualTo(customer.getAddress());
    }

    @Test
    @DisplayName("Test should pass if the findCustomerById method throws an exception for a Customer not found")
    void shouldThrowCustomerNotFound() {
        CustomerService customerService = new CustomerServiceImpl(customerRepository, customerNumberGenerator, mediaRepository);

        Mockito.when(customerRepository.findById("asd987fsad87f0987s")).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() ->
            customerService.getCustomerById("asd987fsad87f0987s")
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Customer not found!");
    }

    @Test
    @DisplayName("Test should pass when the save method returns the ListingDTO with the data from the passed EditingDTO")
    void returnListingDtoWhenSavingEditingDTO() {
        CustomerService customerService = new CustomerServiceImpl(customerRepository, customerNumberGenerator, mediaRepository);

        Mockito.when(customerNumberGenerator.generate()).thenReturn("AJ32LIU98S1W");

        Customer requestedCustomer = new Customer();
            requestedCustomer.setCustomerNumber("AJ32LIU98S1W");
            requestedCustomer.setFirstName("Ivan");
            requestedCustomer.setLastName("Morales");
            requestedCustomer.setAddress("Calle Falsa 123");

        Customer responseCustomer = new Customer("sad98f798sd",
                "AJ32LIU98S1W",
                "Ivan",
                "Morales",
                "Calle Falsa 123");

        Mockito.when(customerRepository.save(requestedCustomer)).thenReturn(responseCustomer);

        CustomerEditingDTO requestedCustomerEditingDto = new CustomerEditingDTO("Ivan",
                "Morales",
                "Calle Falsa 123");

        CustomerListingDTO expectedCustomerListingDto = new CustomerListingDTO("sad98f798sd",
                "AJ32LIU98S1W",
                "Ivan",
                "Morales",
                "Calle Falsa 123");

        CustomerListingDTO actualCustomerListingDto = customerService.save(requestedCustomerEditingDto);

        Assertions.assertThat(actualCustomerListingDto).isEqualTo(expectedCustomerListingDto);
    }
}