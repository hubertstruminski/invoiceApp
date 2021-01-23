package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
