package com.sirius.sentidosapi.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor @AllArgsConstructor
@SQLDelete(sql = "UPDATE customers SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(length = 12, nullable = false, updatable = false, unique = true)
    private String customerNumber;

    private String firstName;

    private String lastName;

    private String address;

    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    public Customer(String id, String customerNumber, String firstName, String lastName, String address) {
        this.setId(id);
        this.setCustomerNumber(customerNumber);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
    }
}
