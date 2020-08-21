package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.util.Constants;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Controller
public class ProductDetailsWindowController implements FxmlController {

    @FXML
    private Label nameMainLabel;

    @FXML
    private Label nameTextField;

    @FXML
    private Label priceTextField;

    @FXML
    private Label amountTextField;

    @FXML
    private Label discountTextField;

    @FXML
    private Label descriptionTextField;

    @FXML
    private Label invoiceValueLabel;

    @FXML
    private Label customerValueLabel;

    @FXML
    private Label unitValueLabel;

    @FXML
    private Label taxValueLabel;

    @Override
    public void initialize() {

    }

    public void setTextFields(Product product) {
        nameMainLabel.setText(product.getName());
        nameTextField.setText(product.getName());
        priceTextField.setText(product.getPrice() + Constants.CURRENCY);
        amountTextField.setText(String.valueOf(product.getAmount()));
        discountTextField.setText(product.getDiscount() + "%");
        descriptionTextField.setText(product.getDescription());
        unitValueLabel.setText(product.getUnit());
        taxValueLabel.setText(product.getTax().getName() + ", Stawka: " + product.getTax().getTaxAmount());

        Invoice invoice = product.getInvoice();

        if(invoice != null) {
            invoiceValueLabel.setText("Numer: " + invoice.getNumber() + ", Data: " + invoice.getDate()
                    + ", Termin: " + invoice.getDeadline());
            customerValueLabel.setText("Nazwa: " + invoice.getCustomer().getName()
                    + ", Telefon: " + invoice.getCustomer().getPhoneNumber());
        } else {
            invoiceValueLabel.setText("Brak");
            customerValueLabel.setText("Brak");
        }
    }
}
