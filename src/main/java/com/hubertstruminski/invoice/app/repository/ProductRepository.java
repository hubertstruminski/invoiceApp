package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.model.Tax;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByInvoice(Invoice invoice);

    List<Product> findAllByTax(Tax tax);
}
