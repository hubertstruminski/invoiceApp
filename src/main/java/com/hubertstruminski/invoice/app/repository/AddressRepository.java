package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
