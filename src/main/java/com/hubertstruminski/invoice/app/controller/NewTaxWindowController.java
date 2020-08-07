package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import com.hubertstruminski.invoice.app.util.Constants;

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

    private final TaxRepository taxRepository;
    private final MainWindowController mainWindowController;

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
    public Label taxNameErrorLabel;

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
    void onNewTaxSaveButtonAction() {
        isTaxNameError = !".{1,255}".matches(nameTextField.getText());
        isTaxDescriptionError = !taxDescriptionErrorLabel.getText().matches(".{0,255}");
        isTaxAmountError = !taxAmountTextField.getText().matches("[0-9]+%$");

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
        taxNameErrorLabel.setText("");
        taxNameErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        taxDescriptionErrorLabel.setText("");
        taxDescriptionErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        taxAmountErrorLabel.setText("");
        taxAmountErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        newTaxIdLabel.setVisible(false);
        isUpdateFlag = false;

        isTaxAmountError = false;
        isTaxDescriptionError = false;
        isTaxNameError = false;
    }

    public void setTextFields(Tax tax) {
        newTaxIdLabel.setText(String.valueOf(tax.getId()));
        nameTextField.setText(tax.getName());
        descriptionTextField.setText(tax.getDescription());
        taxAmountTextField.setText(tax.getTaxAmount());
    }
}
