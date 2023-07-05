package com.tss.repositories;

import com.tss.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
    @Override
    java.util.List<Customer> findAll();
}
