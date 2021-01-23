package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {
}
