package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Tax;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TaxRepository extends CrudRepository<Tax, Long> {

    public List<Tax> findByName(String name);
    public List<Tax> findById(long id);
}
