package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Customer;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@Controller
public class CustomerDetailsWindowController implements FxmlController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailValueLabel;

    @FXML
    private Button emailSendButton;

    @FXML
    private Label phoneNumberValueLabel;

    @FXML
    private Label nipValueLabel;

    @FXML
    private Label addressValueLabel;

    @FXML
    private Label countryValueLabel;

    @FXML
    void onEmailSendButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize() {
        emailSendButton.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.BOOK, "20px"));
        emailSendButton.setAlignment(Pos.BASELINE_LEFT);
        emailSendButton.setPadding(new Insets(10, 20, 10, 20));
    }

    public void setTextFields(Customer customer) {
        nameLabel.setText(customer.getName());
        emailValueLabel.setText(customer.getEmail());
        phoneNumberValueLabel.setText(customer.getPhoneNumber());
        nipValueLabel.setText(customer.getNip());
        addressValueLabel.setText(customer.getAddress().getAddress());
        countryValueLabel.setText(customer.getAddress().getCountry());
    }
}
