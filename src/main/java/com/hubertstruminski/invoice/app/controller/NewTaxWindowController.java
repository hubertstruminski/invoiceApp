package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.fx.manager.MainWindowUiManager;
import com.hubertstruminski.invoice.app.fx.manager.NewTaxWindowUiManager;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import com.hubertstruminski.invoice.app.service.TaxService;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.model.fxml.FxmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class NewTaxWindowController implements FxmlController {

    private boolean isTaxNameError;
    private boolean isTaxDescriptionError;
    private boolean isTaxAmountError;

    @Autowired
    private TaxService taxService;

    @Autowired
    private ViewCreator viewCreator;

    @Autowired
    private EasyFxml easyFxml;

    @Autowired
    private MainWindowUiManager mainWindowUiManager;

    @Autowired
    private NewTaxWindowComponent newTaxWindowComponent;

    @Autowired
    private NewTaxWindowUiManager newTaxWindowUiManager;

    @FXML
    private VBox vBox;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label taxAmountLabel;

    @FXML
    private Label taxAmountErrorLabel;

    @FXML
    private Label taxNameErrorLabel;

    @FXML
    private Label taxDescriptionErrorLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField taxAmountTextField;

    @FXML
    public Button newTaxSaveButton;

    @FXML
    void onNewTaxSaveButtonAction(ActionEvent event) {

        if(!nameTextField.getText().matches(".{1,255}")) {
            isTaxNameError = true;
        } else {
            isTaxNameError = false;
        }

        if(!taxDescriptionErrorLabel.getText().matches(".{0,255}")) {
            isTaxDescriptionError = true;
        } else {
            isTaxDescriptionError = false;
        }

        if(!taxAmountTextField.getText().matches("[0-9]+%$")) {
            isTaxAmountError = true;
        } else {
            isTaxAmountError = false;
        }

        if(isTaxNameError) {
            taxNameErrorLabel.setText("Długość nazwy musi być od 1 do 255 znaków.");
        } else {
            taxNameErrorLabel.setText("");
        }

        if(isTaxDescriptionError) {
            taxDescriptionErrorLabel.setText("Maksymalna długość to 255 znaków.");
        } else {
            taxDescriptionErrorLabel.setText("");
        }

        if(isTaxAmountError) {
            taxAmountErrorLabel.setText("Nieprawidłowy format, np. 23%");
        } else {
            taxAmountErrorLabel.setText("");
        }

        if(!isTaxNameError && !isTaxDescriptionError && !isTaxAmountError) {
            Tax tax = new Tax();
            tax.setName(nameTextField.getText());
            tax.setDescription(descriptionTextField.getText());
            tax.setTaxAmount(taxAmountTextField.getText());

            taxService.save(tax);
        }
    }

    @Override
    public void initialize() {
        String redColorFont = "-fx-text-fill: red; -fx-font-size: 12px;";

        taxNameErrorLabel.setText("");
        taxNameErrorLabel.setStyle(redColorFont);

        taxDescriptionErrorLabel.setText("");
        taxDescriptionErrorLabel.setStyle(redColorFont);

        taxAmountErrorLabel.setText("");
        taxAmountErrorLabel.setStyle(redColorFont);
    }
}
