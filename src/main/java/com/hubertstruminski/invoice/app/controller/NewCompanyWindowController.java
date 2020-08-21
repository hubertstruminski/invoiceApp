package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewNextCompanyErrorWindowComponent;
import com.hubertstruminski.invoice.app.model.Company;
import com.hubertstruminski.invoice.app.repository.CompanyRepository;
import com.hubertstruminski.invoice.app.service.MainWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.stage.Stage;
import javafx.stage.Window;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewCompanyWindowController implements FxmlController {

    private boolean isCompanyNameEmpty = false;
    private boolean isCompanyNameError = false;

    private boolean isAddressEmpty = false;
    private boolean isAddressError = false;

    private boolean isPostalCodeEmpty = false;
    private boolean isPostalCodeError = false;

    private boolean isCityEmpty = false;
    private boolean isCityError = false;

    private boolean isCountryEmpty = false;
    private boolean isCountryError = false;

    private boolean isUpdateFlag = false;

    private final CompanyRepository companyRepository;
    private final CompanyDetailsWindowController companyDetailsWindowController;
    private final CompanyWindowController companyWindowController;
    private final MainWindowController mainWindowController;
    private final MainWindowService mainWindowService;
    private final NewNextCompanyErrorWindowComponent newNextCompanyErrorWindowComponent;

    @Autowired
    public NewCompanyWindowController(
            CompanyRepository companyRepository,
            CompanyDetailsWindowController companyDetailsWindowController,
            @Lazy CompanyWindowController companyWindowController,
            MainWindowController mainWindowController,
            MainWindowService mainWindowService,
            NewNextCompanyErrorWindowComponent newNextCompanyErrorWindowComponent) {
        this.companyRepository = companyRepository;
        this.companyDetailsWindowController = companyDetailsWindowController;
        this.companyWindowController = companyWindowController;
        this.mainWindowController = mainWindowController;
        this.mainWindowService = mainWindowService;
        this.newNextCompanyErrorWindowComponent = newNextCompanyErrorWindowComponent;
    }

    @FXML
    private VBox vBox;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label taxAmountLabel;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField postalcodeTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private Label companyNameErrorLabel;

    @FXML
    private Label addressErrorLabel;

    @FXML
    private Label postalcodeErrorLabel;

    @FXML
    private Label cityErrorLabel;

    @FXML
    private Label countryErrorLabel;

    @FXML
    private Label companyIdLabel;

    @FXML
    void onNewCompanySaveButton() {
        if(companyNameTextField.getText().length() == 0) {
            isCompanyNameEmpty = true;
        } else {
            isCompanyNameEmpty = false;
            if (!companyNameTextField.getText().matches(".{1,255}")) isCompanyNameError = true;
            else isCompanyNameError = false;
        }

        if(addressTextField.getText().length() == 0) {
            isAddressEmpty = true;
        } else {
            isAddressEmpty = false;
            if (!addressTextField.getText().matches(".{1,255}")) isAddressError = true;
            else isAddressError = false;
        }

        if(postalcodeTextField.getText().length() == 0) {
            isPostalCodeEmpty = true;
        } else {
            isPostalCodeEmpty = false;
            if (!postalcodeTextField.getText().matches(".{1,255}")) isPostalCodeError = true;
            else isPostalCodeError = false;
        }

        if(cityTextField.getText().length() == 0) {
            isCityEmpty = true;
        } else {
            isCityEmpty = false;
            if (!cityTextField.getText().matches(".{1,255}")) isCityError = true;
            else isCityError = false;
        }

        if(countryTextField.getText().length() == 0) {
            isCountryEmpty = true;
        } else {
            isCountryEmpty = false;
            if (!countryTextField.getText().matches(".{1,255}")) isCountryError = true;
            else isCountryError = false;
        }

        if(isCompanyNameEmpty) {
            companyNameErrorLabel.setText("Pole nazwa firmy jest wymagane.");
        } else {
            if(isCompanyNameError) {
                companyNameErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                companyNameErrorLabel.setText("");
            }
        }

        if(isAddressEmpty) {
            addressErrorLabel.setText("Pole adres jest wymagane.");
        } else {
            if(isAddressError) {
                addressErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                addressErrorLabel.setText("");
            }
        }

        if(isPostalCodeEmpty) {
            postalcodeErrorLabel.setText("Pole kod pocztowy jest wymagane.");
        } else {
            if(isPostalCodeError) {
                postalcodeErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                postalcodeErrorLabel.setText("");
            }
        }

        if(isCityEmpty) {
            cityErrorLabel.setText("Pole miasto jest wymagane.");
        } else {
            if(isCityError) {
                cityErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                cityErrorLabel.setText("");
            }
        }

        if(isCountryEmpty) {
            countryErrorLabel.setText("Pole kraj jest wymagane.");
        } else {
            if(isCountryError) {
                countryErrorLabel.setText("Wymagana długość od 1 do 255 znaków");
            } else {
                countryErrorLabel.setText("");
            }
        }

        if(!isCompanyNameEmpty && !isCompanyNameError && !isAddressEmpty && !isAddressError && !isPostalCodeEmpty
                && !isPostalCodeError && !isCityEmpty && !isCityError && !isCountryEmpty && !isCountryError) {
            Company company = new Company();
            if(isUpdateFlag) {
                company.setId(Long.parseLong(companyIdLabel.getText()));
            }



            company.setCompanyName(companyNameTextField.getText());
            company.setAddress(addressTextField.getText());
            company.setPostalCode(postalcodeTextField.getText());
            company.setCity(cityTextField.getText());
            company.setCountry(countryTextField.getText());

            Company savedCompany = companyRepository.save(company);

            if(isUpdateFlag) {
                companyWindowController.onSubViewChange();
                companyDetailsWindowController.setTextFields(savedCompany);
            }

            mainWindowController.refreshCompanyTableView();

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize() {
        companyNameErrorLabel.setText("");
        companyNameErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        addressErrorLabel.setText("");
        addressErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        postalcodeErrorLabel.setText("");
        postalcodeErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        cityErrorLabel.setText("");
        cityErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        countryErrorLabel.setText("");
        countryErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        isCompanyNameEmpty = false;
        isCompanyNameError = false;

        isAddressEmpty = false;
        isAddressError = false;

        isPostalCodeEmpty = false;
        isPostalCodeError = false;

        isCityEmpty = false;
        isCityError = false;

        isCountryEmpty = false;
        isCountryError = false;

        isUpdateFlag = false;

        companyIdLabel.setVisible(false);
    }

    public void setTextFields(Company company) {
        companyIdLabel.setText(String.valueOf(company.getId()));
        companyNameTextField.setText(company.getCompanyName());
        addressTextField.setText(company.getAddress());
        postalcodeTextField.setText(company.getPostalCode());
        cityTextField.setText(company.getCity());
        countryTextField.setText(company.getCountry());
    }

    public void setUpdateFlag(boolean isUpdateFlag) {
        this.isUpdateFlag = isUpdateFlag;
    }
}
