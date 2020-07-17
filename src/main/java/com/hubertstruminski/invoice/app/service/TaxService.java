package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaxService {

    @Autowired
    private TaxRepository taxRepository;

    @Transactional
    public void save(Tax tax) {
        taxRepository.save(tax);
    }
}
