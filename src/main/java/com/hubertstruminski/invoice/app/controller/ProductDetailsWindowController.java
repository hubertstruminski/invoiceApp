package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Product;
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

    @Override
    public void initialize() {

    }

    public void setTextFields(Product product) {
        nameMainLabel.setText(product.getName());
        nameTextField.setText(product.getName());
        priceTextField.setText(product.getPrice() + " NOK");
        amountTextField.setText(String.valueOf(product.getAmount()));
        discountTextField.setText(product.getDiscount() + "%");
        descriptionTextField.setText(product.getDescription());
    }
}
