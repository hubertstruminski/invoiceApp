package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewInvoiceWindowService {

    public String renderTextForProductButton(List<Product> products) {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<products.size(); i++) {
            if(i == products.size() - 1) {
                builder.append(products.get(i).getName());
                break;
            }
            builder.append(products.get(i).getName()).append(", ");
        }
        return builder.toString();
    }
}
