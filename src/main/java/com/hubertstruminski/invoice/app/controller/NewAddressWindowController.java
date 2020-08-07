package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Address;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Controller
public class NewAddressWindowController implements FxmlController {

    private boolean isAddressError = false;
    private boolean isCountryError = false;
    private boolean isUpdateFlag = false;

    private Address address = null;

    private final NewCustomerWindowController newCustomerWindowController;

    @Autowired
    public NewAddressWindowController(NewCustomerWindowController newCustomerWindowController) {
        this.newCustomerWindowController = newCustomerWindowController;
    }

    @FXML
    private VBox vBox;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private Label countryErrorLabel;

    @FXML
    private Label addressIdLabel;

    public void setUpdateFlag(boolean updateFlag) {
        isUpdateFlag = updateFlag;
    }

    @FXML
    void onNewAddressSaveButtonAction() {
        if (!addressTextField.getText().matches(".{1,255}")) isAddressError = true;
        else isAddressError = false;

        if (!countryTextField.getText().matches(".{0,255}")) isCountryError = true;
        else isCountryError = false;

        if(isAddressError) {
            addressErrorLabel.setText("Długość adresu musi być od 1 do 255 znaków.");
        } else {
            addressErrorLabel.setText("");
        }

        if(isCountryError) {
            countryErrorLabel.setText("Maksymalna długość to 255 znaków.");
        } else {
            countryErrorLabel.setText("");
        }

        if(!isAddressError && !isCountryError) {
            if(newCustomerWindowController.isUpdateFlagWithTableView()) {
                address.setId(Long.parseLong(addressIdLabel.getText()));
            }
            address.setAddress(addressTextField.getText());
            address.setCountry(countryTextField.getText());

            newCustomerWindowController.setCustomerAddress(address);

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize() {
        addressErrorLabel.setText("");
        addressErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        countryErrorLabel.setText("");
        countryErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        isUpdateFlag = false;
        addressIdLabel.setVisible(false);
        address = new Address();

        isCountryError = false;
        isAddressError = false;
    }

    public void setTextFieldsForUpdateAddress(Address address) {
        addressTextField.setText(address.getAddress());
        countryTextField.setText(address.getCountry());
    }

    public void invokeSetTextFieldsForUpdateAddress() {
        if(isUpdateFlag) {
            address = newCustomerWindowController.returnAddressObjectForUpdate();

            if(address != null) {
                setTextFieldsForUpdateAddress(address);
            }
        }
    }

    public void setTextFields(Address address) {
        addressIdLabel.setText(String.valueOf(address.getId()));
        addressTextField.setText(address.getAddress());
        countryTextField.setText(address.getCountry());
    }
}
