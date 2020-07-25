package com.hubertstruminski.invoice.app.controller;

import javafx.event.ActionEvent;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


@Controller
public class NewCustomerWindowController implements FxmlController {

    @FXML
    private VBox vBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button newCustomerSaveButton;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField taxAmountTextField11;

    @FXML
    private TextField nipTextField;

    @FXML
    private TextField noteTextField;

    @FXML
    void onAddressTextFieldAction(ActionEvent event) {

    }

    @FXML
    void onNewCustomerSaveButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize() {

    }
}
