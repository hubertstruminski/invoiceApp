package com.hubertstruminski.invoice.app.repository;

import com.hubertstruminski.invoice.app.model.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
