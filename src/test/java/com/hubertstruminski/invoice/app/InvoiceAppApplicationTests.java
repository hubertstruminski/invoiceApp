package com.hubertstruminski.invoice.app;

import com.hubertstruminski.invoice.app.repository.TaxRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

//@SpringBootTest
@DataJpaTest
@Import(TaxRepository.class)
class InvoiceAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
