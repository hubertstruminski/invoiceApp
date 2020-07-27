package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewAddressWindowComponent;
import com.hubertstruminski.invoice.app.model.Address;
import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.service.MainService;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


@Controller
public class NewCustomerWindowController implements FxmlController {

    private boolean isNewAddressStageOpen = false;

    private Customer customer = null;

    private MainService mainService;
    private NewAddressWindowComponent newAddressWindowComponent;
    private NewAddressWindowController newAddressWindowController;

    @Autowired
    public NewCustomerWindowController(
            MainService mainService,
            NewAddressWindowComponent newAddressWindowComponent,
            @Lazy NewAddressWindowController newAddressWindowController) {
        this.mainService = mainService;
        this.newAddressWindowComponent = newAddressWindowComponent;
        this.newAddressWindowController = newAddressWindowController;
    }

    @FXML
    private VBox vBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

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
    private Button newAddressButton;

    @FXML
    void onNewAddressButtonAction(ActionEvent event) {
        mainService.onLoadComponent(
                newAddressWindowComponent,
                400,
                300,
                false,
                "Nowy adres"
        );
        if(!isNewAddressStageOpen) {
            isNewAddressStageOpen = true;
        } else {
            newAddressWindowController.setUpdateFlag(true);
            newAddressWindowController.invokeSetTextFieldsForUpdateAddress();
        }
    }

    @FXML
    void onNewCustomerSaveButtonAction(ActionEvent event) {

    }

    @Override
    public void initialize() {
        customer = new Customer();
        isNewAddressStageOpen = false;
    }

    public void setCustomerAddress(Address address) {
        customer.setAddress(address);
        newAddressButton.setText(address.getAddress() + ", " + address.getCountry());
    }

    public Address returnAddressObjectForUpdate() {
        return customer.getAddress();
    }
}
