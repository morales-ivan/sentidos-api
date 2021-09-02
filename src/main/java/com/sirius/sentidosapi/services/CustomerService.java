package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.customer.CustomerEditingDTO;
import com.sirius.sentidosapi.model.customer.CustomerListingDTO;
import com.sirius.sentidosapi.model.media.Media;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Page<CustomerListingDTO> getCustomers(Integer pageSize, Integer pageNumber);

    CustomerListingDTO getCustomerById(String id);

    CustomerListingDTO save(CustomerEditingDTO requestedCustomer);

    void updateCustomer(String id, CustomerEditingDTO requestedCustomer);

    void deleteCustomer(String id);

    void addMedia(String customerId, Media savedMedia);

    void removeMedia(Media media);
}
