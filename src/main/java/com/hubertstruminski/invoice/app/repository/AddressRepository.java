package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
