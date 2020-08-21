package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.service.InvoiceDetailsWindowService;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import java.util.List;

@Controller
public class InvoiceDetailsWindowController implements FxmlController {

    private final InvoiceDetailsWindowService invoiceDetailsWindowService;

    @Autowired
    public InvoiceDetailsWindowController(InvoiceDetailsWindowService invoiceDetailsWindowService) {
        this.invoiceDetailsWindowService = invoiceDetailsWindowService;
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

        List<Product> products = invoiceDetailsWindowService.findProducts(invoice);

        for(Product product: products) {
            GridPane gridPane = invoiceDetailsWindowService.setGridPane(product);
            VBox vBox = invoiceDetailsWindowService.createVBox(gridPane);
            mainVBox.getChildren().add(vBox);
        }
    }


}
