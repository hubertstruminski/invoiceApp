package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Tax;
//import com.hubertstruminski.invoice.app.repository.TaxRepository;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import com.hubertstruminski.invoice.app.service.TaxService;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Scope("prototype")
public class NewTaxWindowController extends BaseController implements Initializable {

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxService taxService;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label taxAmountLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField taxAmountTextField;

    @FXML
    private Button newTaxSaveButton;

    @FXML
    void onNewTaxSaveButtonAction(ActionEvent event) {
        Tax tax = new Tax();
        tax.setName(nameTextField.getText());
        tax.setDescription(descriptionTextField.getText());
        tax.setTaxAmount(taxAmountTextField.getText());


        taxRepository.save(tax);
    }

    public NewTaxWindowController() {

    }

    public NewTaxWindowController(ViewCreator viewCreator, String fxmlName) {
        super(viewCreator, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
