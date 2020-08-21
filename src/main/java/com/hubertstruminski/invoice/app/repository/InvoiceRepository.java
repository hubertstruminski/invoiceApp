package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.model.Invoice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findAllByCustomer(Customer customer);
}
