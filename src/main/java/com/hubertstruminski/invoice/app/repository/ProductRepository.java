package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
