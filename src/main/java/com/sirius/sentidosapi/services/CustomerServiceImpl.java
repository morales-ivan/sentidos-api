package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.customer.Customer;
import com.sirius.sentidosapi.model.customer.CustomerEditingDTO;
import com.sirius.sentidosapi.model.customer.CustomerListingDTO;
import com.sirius.sentidosapi.model.generators.CustomerNumberGenerator;
import com.sirius.sentidosapi.model.media.Media;
import com.sirius.sentidosapi.repositories.CustomerRepository;
import com.sirius.sentidosapi.repositories.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerNumberGenerator customerNumberGenerator;
    private final MediaRepository mediaRepository;

    // TODO fix tests
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerNumberGenerator customerNumberGenerator,
                               MediaRepository mediaRepository) {
        this.customerRepository = customerRepository;
        this.customerNumberGenerator = customerNumberGenerator;
        this.mediaRepository = mediaRepository;
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
        Customer customer = safeGetCustomer(id);
        return CustomerListingDTO.fromCustomer(customer);
    }

    @Override
    public CustomerListingDTO save(CustomerEditingDTO requestedCustomer) {
        Customer customer = new Customer();
        Set<Media> mediaSet = getMediaSet(requestedCustomer);


        customer.setCustomerNumber(customerNumberGenerator.generate());
        customer.setFirstName(requestedCustomer.getFirstName());
        customer.setLastName(requestedCustomer.getLastName());
        customer.setAddress((requestedCustomer.getAddress()));
        customer.setMedia(mediaSet);

        return CustomerListingDTO.fromCustomer(customerRepository.save(customer));
    }

    @Override
    public void updateCustomer(String id, CustomerEditingDTO requestedCustomer) {
        Customer customerFromDb = safeGetCustomer(id);

        Set<Media> mediaSet = getMediaSet(requestedCustomer);

        customerFromDb.setFirstName(requestedCustomer.getFirstName());
        customerFromDb.setLastName(requestedCustomer.getLastName());
        customerFromDb.setAddress(requestedCustomer.getAddress());
        customerFromDb.setMedia(mediaSet);

        customerRepository.save(customerFromDb);
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void addMedia(String customerId, Media savedMedia) {
        Customer customer = safeGetCustomer(customerId);
        Set<Media> customerMediaSet = customer.getMedia();
        if (customerMediaSet.add(savedMedia)) {
            customerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("The customer already had this media associated!");
        }
    }

    @Override
    public void removeMedia(Media media) {
        Customer customer = customerRepository.findMediaOwner(media.getId());
        Set<Media> customerMedia = customer.getMedia();
        if (customerMedia.remove(media))
            customer.setMedia(customerMedia);
        else
            throw new IllegalArgumentException("Customer found didn't have that media associated");
    }


    private Customer safeGetCustomer(String customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not found!"));
    }

    private Set<Media> getMediaSet(CustomerEditingDTO requestedCustomer) {
        if (requestedCustomer.getMediaIds() == null) return new HashSet<>();
        else return requestedCustomer.getMediaIds().isEmpty() ?
                new HashSet<>() : mediaRepository.findAllByIdIn(requestedCustomer.getMediaIds());
    }
}
