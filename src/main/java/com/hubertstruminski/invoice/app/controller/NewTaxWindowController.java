package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NewTaxWindowController implements FxmlController {

    private boolean isTaxNameError;
    private boolean isTaxDescriptionError;
    private boolean isTaxAmountError;

    private boolean isUpdateFlag = false;

    private TaxRepository taxRepository;
    private MainWindowController mainWindowController;

    @Autowired
    public NewTaxWindowController(
            TaxRepository taxRepository,
            MainWindowController mainWindowController) {
        this.taxRepository = taxRepository;
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private VBox vBox;

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
    private Label newTaxIdLabel;

    public void setUpdateFlag(boolean updateFlag) {
        isUpdateFlag = updateFlag;
    }

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
            if(isUpdateFlag) {
                tax.setId(Long.parseLong(newTaxIdLabel.getText()));
            }
            tax.setName(nameTextField.getText());
            tax.setDescription(descriptionTextField.getText());
            tax.setTaxAmount(taxAmountTextField.getText());

            taxRepository.save(tax);
            mainWindowController.refreshTaxTableView();

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
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

        newTaxIdLabel.setVisible(false);
        isUpdateFlag = false;
    }

    public void setTextFields(Tax tax) {
        newTaxIdLabel.setText(String.valueOf(tax.getId()));
        nameTextField.setText(tax.getName());
        descriptionTextField.setText(tax.getDescription());
        taxAmountTextField.setText(tax.getTaxAmount());
    }
}
