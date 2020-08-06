package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewAddressWindowComponent;
import com.hubertstruminski.invoice.app.model.Address;
import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.AddressRepository;
import com.hubertstruminski.invoice.app.repository.CustomerRepository;
import com.hubertstruminski.invoice.app.service.MainService;
import javafx.scene.control.Label;
import javafx.stage.Stage;
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
    private boolean isUpdateFlag = false;
    private boolean isUpdateFlagWithTableView = false;

    private boolean isNameError;
    private boolean isNameEmpty;

    private boolean isEmailError;
    private boolean isEmailEmpty;

    private boolean isAddressError;
    private boolean isAddressEmpty;

    private boolean isNipError;
    private boolean isNipEmpty;

    private Customer customer = null;
    private Address address = null;

    private MainService mainService;
    private NewAddressWindowComponent newAddressWindowComponent;
    private NewAddressWindowController newAddressWindowController;
    private AddressRepository addressRepository;
    private CustomerRepository customerRepository;
    private MainWindowController mainWindowController;

    @Autowired
    public NewCustomerWindowController(
            MainService mainService,
            NewAddressWindowComponent newAddressWindowComponent,
            @Lazy NewAddressWindowController newAddressWindowController,
            AddressRepository addressRepository,
            CustomerRepository customerRepository,
            MainWindowController mainWindowController) {
        this.mainService = mainService;
        this.newAddressWindowComponent = newAddressWindowComponent;
        this.newAddressWindowController = newAddressWindowController;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.mainWindowController = mainWindowController;
    }

    public void setUpdateFlagWithTableView(boolean updateFlagWithTableView) {
        isUpdateFlagWithTableView = updateFlagWithTableView;
    }

    public boolean isUpdateFlagWithTableView() {
        return isUpdateFlagWithTableView;
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
    private TextField websiteTextField;

    @FXML
    private TextField nipTextField;

    @FXML
    private TextField noteTextField;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private Label nipErrorLabel;

    @FXML
    private Button newAddressButton;

    @FXML
    private Label customerIdLabel;

    public void setUpdateFlag(boolean updateFlag) {
        isUpdateFlag = updateFlag;
    }

    @FXML
    void onNewAddressButtonAction(ActionEvent event) {
        mainService.onLoadComponent(
                newAddressWindowComponent,
                400,
                300,
                false,
                "Nowy adres"
        );
        if(isUpdateFlagWithTableView) {
            setTextFieldForNewAddressWindow(address);
            return;
        }

        if(!isNewAddressStageOpen) {
            isNewAddressStageOpen = true;
        } else {
            newAddressWindowController.setUpdateFlag(true);
            newAddressWindowController.invokeSetTextFieldsForUpdateAddress();
        }
    }

    @FXML
    void onNewCustomerSaveButtonAction(ActionEvent event) {
        if(nameTextField.getText().length() == 0) {
            isNameEmpty = true;
        } else {
            isNameEmpty = false;
            if(!nameTextField.getText().matches(".{1,255}")) {
                isNameError = true;
            } else {
                isNameError = false;
            }
        }

        if(emailTextField.getText().length() == 0) {
            isEmailEmpty = true;
        } else {
            isEmailEmpty = false;
            if(!emailTextField.getText().matches(".{1,255}")) {
                isEmailError = true;
            } else {
                isEmailError = false;
            }
        }

        if(newAddressButton.getText().length() == 1) {
            isAddressEmpty = true;
        } else {
            isAddressEmpty = false;
        }

        if(nipTextField.getText().length() == 0) {
            isNipEmpty = true;
        } else {
            isNipEmpty = false;
            if(!nipTextField.getText().matches(".{1,255}")) {
                isNipError = true;
            } else {
                isNipError = false;
            }
        }

        if(isNameEmpty) {
            nameErrorLabel.setText("Pole nazwa jest wymagane.");
        } else {
            if(isNameError) {
                nameErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                nameErrorLabel.setText("");
            }
        }

        if(isEmailEmpty) {
            emailErrorLabel.setText("Pole email jest wymagane.");
        } else {
            if(isEmailError) {
                emailErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                emailErrorLabel.setText("");
            }
        }

        if(isAddressEmpty) {
            addressErrorLabel.setText("Pole adres jest wymagane.");
        } else {
            addressErrorLabel.setText("");
        }

        if(isNipEmpty) {
            nipErrorLabel.setText("Pole NIP jest wymagane.");
        } else {
            if(isNipError) {
                nipErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                nipErrorLabel.setText("");
            }
        }

        if(!isNameEmpty && !isNameError && !isEmailEmpty && !isEmailError && !isAddressEmpty && !isNipEmpty && !isNipError) {
            Address savedAddress = addressRepository.save(address);

            if(isUpdateFlag) {
                customer.setId(Long.parseLong(customerIdLabel.getText()));
            }

            customer.setName(nameTextField.getText());
            customer.setEmail(emailTextField.getText());
            customer.setAddress(savedAddress);
            customer.setPhoneNumber(phoneTextField.getText());
            customer.setWebsite(websiteTextField.getText());
            customer.setNip(nipTextField.getText());
            customer.setNote(noteTextField.getText());

            customerRepository.save(customer);
            mainWindowController.refreshCustomerTableView();

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize() {
        String redColorFont = "-fx-text-fill: red; -fx-font-size: 12px;";

        customer = new Customer();
        address = new Address();
        isNewAddressStageOpen = false;

        customerIdLabel.setVisible(false);
        isUpdateFlag = false;
        isUpdateFlagWithTableView = false;

        isNameError = false;
        isNameEmpty = false;

        isEmailError = false;
        isEmailEmpty = false;

        isAddressError = false;
        isAddressEmpty = false;

        isNipError = false;
        isNipEmpty = false;

        nameErrorLabel.setText("");
        nameErrorLabel.setStyle(redColorFont);

        emailErrorLabel.setText("");
        emailErrorLabel.setStyle(redColorFont);

        addressErrorLabel.setText("");
        addressErrorLabel.setStyle(redColorFont);

        nipErrorLabel.setText("");
        nipErrorLabel.setStyle(redColorFont);
    }

    public void setCustomerAddress(Address _address) {
        address = _address;
        newAddressButton.setText(address.getAddress() + ", " + address.getCountry());
    }

    public Address returnAddressObjectForUpdate() {
        return address;
    }

    public void setTextFields(Customer customer) {
        customerIdLabel.setText(String.valueOf(customer.getId()));
        nameTextField.setText(customer.getName());
        emailTextField.setText(customer.getEmail());
        newAddressButton.setText(customer.getAddress().getAddress() + " " + customer.getAddress().getCountry());
        phoneTextField.setText(customer.getPhoneNumber());
        websiteTextField.setText(customer.getWebsite());
        nipTextField.setText(customer.getNip());
        noteTextField.setText(customer.getNote());
    }

    public void setTextFieldForNewAddressWindow(Address address) {
        newAddressWindowController.setTextFields(address);
    }
}
