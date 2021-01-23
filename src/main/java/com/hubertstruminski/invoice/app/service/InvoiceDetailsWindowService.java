package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceDetailsWindowService {

    private final ProductRepository productRepository;

    @Autowired
    public InvoiceDetailsWindowService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public GridPane setGridPane(Product product) {
        GridPane gridPane = new GridPane();

        gridPane.add(new Label(product.getName()), 0, 0);
        gridPane.add(new Label("Ilość: " + product.getAmount()), 0 ,1);
        gridPane.add(new Label("Cena: " + product.getPrice() + Constants.CURRENCY), 0 ,2);

        return gridPane;
    }

    public VBox createVBox(GridPane gridPane) {
        VBox vBox = new VBox(gridPane);
        vBox.setFillWidth(true);

        vBox.setStyle(Constants.INVOICE_DETAILS_PRODUCTS_ITEMS);
        return vBox;
    }

    public List<Product> findProducts(Invoice invoice) {
        Iterable<Product> productsIterable = productRepository.findAllByInvoice(invoice);
        List<Product> products = new ArrayList<>();
        productsIterable.forEach(products::add);
        return products;
    }
}
