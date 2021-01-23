package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Company;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.stereotype.Controller;

@Controller
public class CompanyDetailsWindowController implements FxmlController {

    @FXML
    private Label companyNameMainLabel;

    @FXML
    private Label companyNameTextField;

    @FXML
    private Label addressTextField;

    @FXML
    private Label postalCodeTextField;

    @FXML
    private Label cityTextField;

    @FXML
    private Label countryTextField;

    @Override
    public void initialize() {

    }

    public void setTextFields(Company company) {
        companyNameMainLabel.setText(company.getCompanyName());
        companyNameTextField.setText(company.getCompanyName());
        addressTextField.setText(company.getAddress());
        postalCodeTextField.setText(company.getPostalCode());
        cityTextField.setText(company.getCity());
        countryTextField.setText(company.getCountry());
    }
}
