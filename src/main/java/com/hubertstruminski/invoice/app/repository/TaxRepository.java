package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Tax;

import org.springframework.data.repository.CrudRepository;

public interface TaxRepository extends CrudRepository<Tax, Long> {
}
