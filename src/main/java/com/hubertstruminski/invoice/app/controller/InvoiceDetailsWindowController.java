package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InvoiceDetailsWindowController implements FxmlController {
    private final ProductRepository productRepository;

    @Autowired
    public InvoiceDetailsWindowController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @FXML
    private Label numberInvoiceValueLabel;

    @FXML
    private Label dateValueLabel;

    @FXML
    private Label deadlineValueLabel;

    @FXML
    private Label customerValueLabel;

    @FXML
    private Label commentValueLabel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label invoiceIdLabel;

    @Override
    public void initialize() {
        invoiceIdLabel.setVisible(false);
    }

    public void setTextFields(Invoice invoice) {
        invoiceIdLabel.setText(String.valueOf(invoice.getId()));
        numberInvoiceValueLabel.setText(invoice.getNumber());
        dateValueLabel.setText(String.valueOf(invoice.getDate()));
        deadlineValueLabel.setText(String.valueOf(invoice.getDeadline()));
        customerValueLabel.setText(invoice.getCustomer().getName() + ", " + invoice.getCustomer().getAddress() .getAddress()
                + " " + invoice.getCustomer().getAddress().getCountry());
        commentValueLabel.setText(invoice.getComment());

        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        Iterable<Product> productsIterable = productRepository.findAllByInvoice(invoice);
        List<Product> products = new ArrayList<>();
        productsIterable.forEach(products::add);

        for(Product product: products) {
            GridPane gridPane = new GridPane();

            gridPane.add(new Label(product.getName()), 0, 0);
            gridPane.add(new Label("Ilość: " + product.getAmount()), 0 ,1);
            gridPane.add(new Label("Cena: " + product.getPrice() + Constants.CURRENCY), 0 ,2);

            VBox vBox = new VBox(gridPane);
            vBox.setFillWidth(true);

            vBox.setStyle(Constants.INVOICE_DETAILS_PRODUCTS_ITEMS);
            mainVBox.getChildren().add(vBox);
        }
    }
}
