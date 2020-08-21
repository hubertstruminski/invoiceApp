package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.ChooseTaxWindowComponent;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import com.hubertstruminski.invoice.app.service.MainWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

@Controller
public class NewProductWindowController implements FxmlController {

    private boolean isNameEmpty = false;
    private boolean isNameError = false;

    private boolean isDescriptionEmpty = false;

    private boolean isPriceEmpty = false;
    private boolean isPriceError = false;

    private boolean isDiscountError = false;

    private boolean isAmountEmpty = false;
    private boolean isAmountError = false;

    private boolean isUnitEmpty = false;
    private boolean isUnitError = false;

    private boolean isTaxEmpty = false;

    private boolean isUpdateFlag = false;

    private Tax tax = null;
    private Product product = null;

    private final ProductRepository productRepository;
    private final MainWindowController mainWindowController;
    private final ChooseTaxWindowComponent chooseTaxWindowComponent;
    private final MainWindowService mainWindowService;
    private final TaxRepository taxRepository;

    @Autowired
    public NewProductWindowController(
            ProductRepository productRepository,
            MainWindowController mainWindowController,
            ChooseTaxWindowComponent chooseTaxWindowComponent,
            MainWindowService mainWindowService,
            TaxRepository taxRepository) {
        this.productRepository = productRepository;
        this.mainWindowController = mainWindowController;
        this.chooseTaxWindowComponent = chooseTaxWindowComponent;
        this.mainWindowService = mainWindowService;
        this.taxRepository = taxRepository;
    }

    @FXML
    private VBox vBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField discountTextField;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private Label descriptionErrorLabel;

    @FXML
    private Label priceErrorLabel;

    @FXML
    private Label amountErrorLabel;

    @FXML
    private Label discountErrorLabel;

    @FXML
    private Label productIdLabel;

    @FXML
    private TextField unitTextField;

    @FXML
    private Label errorUnitLabel;

    @FXML
    private Button addTaxButton;

    @FXML
    private Label taxErrorLabel;

    public void setUpdateFlag(boolean updateFlag) {
        isUpdateFlag = updateFlag;
    }

    @FXML
    void onAddTaxButtonAction() {
        mainWindowService.onLoadComponent(
                chooseTaxWindowComponent,
                400,
                350,
                false,
                "Wybierz podatek");
    }

    @FXML
    void onNewProductSaveButton() {
        if(nameTextField.getText().length() == 0) {
            isNameEmpty = true;
        } else {
            isNameEmpty = false;
            if (!nameTextField.getText().matches(".{1,255}")) isNameError = true;
            else isNameError = false;
        }

        if (!descriptionTextField.getText().matches(".{0,255}")) isDescriptionEmpty = true;
        else isDescriptionEmpty = false;

        if(priceTextField.getText().length() == 0) {
            isPriceEmpty = true;
        } else {
            isPriceEmpty = false;
            if (!priceTextField.getText().matches("\\d+(\\.\\d{1,2})?")) isPriceError = true;
            else isPriceError = false;
        }

        if(discountTextField.getText().length() > 0) {
            if (!discountTextField.getText().matches("[0-9]{1,3}")) isDiscountError = true;
            else isDiscountError = false;
        }

        if(amountTextField.getText().length() == 0) {
            isAmountEmpty = true;
        } else {
            isAmountEmpty = false;
            if (!amountTextField.getText().matches("\\d+(\\.\\d{1,2})?")) isAmountError = true;
            else isAmountError = false;
        }

        if(unitTextField.getText().length() == 0) {
            isUnitEmpty = true;
        } else {
            isUnitEmpty = false;
            if (!unitTextField.getText().matches(".{1,255}")) isUnitError = true;
            else isUnitError = false;
        }

        if(addTaxButton.getText().equalsIgnoreCase(">")) isTaxEmpty = true;
        else isTaxEmpty = false;

        if(isNameEmpty) {
            nameErrorLabel.setText("Pole nazwa jest wymagane.");
        } else {
            if(isNameError) {
                nameErrorLabel.setText("Wymagana długość od 1 do 255 znaków.");
            } else {
                nameErrorLabel.setText("");
            }
        }

        if(isDescriptionEmpty) {
            descriptionErrorLabel.setText("Maksymalna długość to 255 znaków.");
        } else {
            descriptionErrorLabel.setText("");
        }

        if(isPriceEmpty) {
            priceErrorLabel.setText("Pole cena jest wymagane.");
        } else {
            if(isPriceError) {
                priceErrorLabel.setText("Wartość musi być zawierać kropkę lub liczbę całkowitą");
            } else {
                priceErrorLabel.setText("");
            }
        }

        if(isDiscountError) {
            discountErrorLabel.setText("Wymagana liczba całkowita od 0 do 100.");
        } else {
            discountErrorLabel.setText("");
        }

        if(isAmountEmpty) {
            amountErrorLabel.setText("Pole ilość jest wymagane.");
        } else {
            if(isAmountError) {
                amountErrorLabel.setText("Wartość musi być zawierać kropkę lub liczbę całkowitą");
            } else {
                amountErrorLabel.setText("");
            }
        }

        if(isUnitEmpty) {
            errorUnitLabel.setText("Pole jednostka jest wymagane.");
        } else {
            if(isUnitError) {
                errorUnitLabel.setText("Wymagana długość od 1 do 255 znaków.");
            } else {
                errorUnitLabel.setText("");
            }
        }

        if(isTaxEmpty) {
            taxErrorLabel.setText("Pole podatek jest wymagane.");
        } else {
            taxErrorLabel.setText("");
        }

        if(!isNameEmpty && !isNameError && !isDescriptionEmpty && !isPriceEmpty && !isTaxEmpty &&
                !isPriceError && !isDiscountError && !isAmountEmpty && !isAmountError && !isUnitError && !isUnitEmpty) {
            if(isUpdateFlag) {
                product.setId(Long.parseLong(productIdLabel.getText()));
            }
            product.setName(nameTextField.getText());
            product.setDescription(descriptionTextField.getText());
            product.setPrice(new BigDecimal(priceTextField.getText()));
            product.setAmount(Double.parseDouble(amountTextField.getText()));
            product.setUnit(unitTextField.getText());

            if(discountTextField.getText().length() != 0) {
                product.setDiscount(Integer.parseInt(discountTextField.getText()));
            }
            product.setTax(tax);
            taxRepository.save(tax);

            productRepository.save(product);
            mainWindowController.refreshProductTableView();

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize() {
        tax = new Tax();
        product = new Product();
        isNameEmpty = false;
        isNameError = false;

        isDescriptionEmpty = false;

        isPriceEmpty = false;
        isPriceError = false;

        isDiscountError = false;

        isAmountEmpty = false;
        isAmountError = false;

        isUnitEmpty = false;
        isUnitError = false;

        isTaxEmpty = false;

        productIdLabel.setVisible(false);
        isUpdateFlag = false;

        nameErrorLabel.setText("");
        nameErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        descriptionErrorLabel.setText("");
        descriptionErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        priceErrorLabel.setText("");
        priceErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        discountErrorLabel.setText("");
        discountErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        amountErrorLabel.setText("");
        amountErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        errorUnitLabel.setText("");
        errorUnitLabel.setStyle(Constants.RED_COLOR_FONT);

        taxErrorLabel.setText("");
        taxErrorLabel.setStyle(Constants.RED_COLOR_FONT);
    }

    public void setTax(Tax _tax) {
        tax.setId(_tax.getId());
        tax.setTaxAmount(_tax.getTaxAmount());
        tax.setDescription(_tax.getDescription());
        tax.setName(_tax.getName());

        addTaxButton.setText(_tax.getName() + " Stawka: " + _tax.getTaxAmount());
    }

    public void setTextFields(Product _product) {
        productIdLabel.setText(String.valueOf(_product.getId()));
        nameTextField.setText(_product.getName());
        descriptionTextField.setText(_product.getDescription());
        priceTextField.setText(String.valueOf(_product.getPrice()));
        amountTextField.setText(String.valueOf(_product.getAmount()));
        discountTextField.setText(String.valueOf(_product.getDiscount()));
        unitTextField.setText(_product.getUnit());

        addTaxButton.setText(_product.getTax().getName() + " Stawka: " + _product.getTax().getTaxAmount());

        product = _product;
    }
}
