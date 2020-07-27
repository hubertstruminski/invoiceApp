package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Address;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.AddressRepository;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Controller
public class NewAddressWindowController implements FxmlController {

    private boolean isAddressError = false;
    private boolean isCountryError = false;
    private boolean isUpdateFlag = false;

    private Address address = null;

    private AddressRepository addressRepository;
    private NewCustomerWindowController newCustomerWindowController;

    @Autowired
    public NewAddressWindowController(
            AddressRepository addressRepository,
            NewCustomerWindowController newCustomerWindowController) {
        this.addressRepository = addressRepository;
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

    @FXML
    private Button newAddressSaveButton;

    public void setUpdateFlag(boolean updateFlag) {
        isUpdateFlag = updateFlag;
    }

    @FXML
    void onNewAddressSaveButtonAction(ActionEvent event) {
        if(!addressTextField.getText().matches(".{1,255}")) {
            isAddressError = true;
        } else {
            isAddressError = false;
        }

        if(!countryTextField.getText().matches(".{0,255}")) {
            isCountryError = true;
        } else {
            isCountryError = false;
        }

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
            address.setAddress(addressTextField.getText());
            address.setCountry(countryTextField.getText());

            Address savedAddress = addressRepository.save(address);
            newCustomerWindowController.setCustomerAddress(savedAddress);

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize() {
        String redColorFont = "-fx-text-fill: red; -fx-font-size: 12px;";

        addressErrorLabel.setText("");
        addressErrorLabel.setStyle(redColorFont);

        countryErrorLabel.setText("");
        countryErrorLabel.setStyle(redColorFont);

        addressIdLabel.setVisible(false);
        isUpdateFlag = false;

        address = new Address();
    }

    public void setTextFieldsForUpdateAddress(Address address) {
        addressTextField.setText(address.getAddress());
        countryTextField.setText(address.getCountry());
        addressIdLabel.setText(String.valueOf(address.getId()));
    }

    public void invokeSetTextFieldsForUpdateAddress() {
        if(isUpdateFlag) {
            address = newCustomerWindowController.returnAddressObjectForUpdate();

            if(address != null) {
                setTextFieldsForUpdateAddress(address);
            }
        }
    }
}
