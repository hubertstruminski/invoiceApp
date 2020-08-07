package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.util.Constants;
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

    private boolean isUpdateFlag = false;

    private final ProductRepository productRepository;
    private final MainWindowController mainWindowController;

    @Autowired
    public NewProductWindowController(
            ProductRepository productRepository,
            MainWindowController mainWindowController) {
        this.productRepository = productRepository;
        this.mainWindowController = mainWindowController;
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

    public void setUpdateFlag(boolean updateFlag) {
        isUpdateFlag = updateFlag;
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
            if (!amountTextField.getText().matches("[0-9]+")) isAmountError = true;
            else isAmountError = false;
        }

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
            descriptionErrorLabel.setText("");
        } else {
            descriptionErrorLabel.setText("Wymagana długość od 1 do 255 znaków.");
        }

        if(isPriceEmpty) {
            priceErrorLabel.setText("Pole cena jest wymagane.");
        } else {
            if(isPriceError) {
                priceErrorLabel.setText("Wartość musi być np. 7412.36 lub 7412");
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
                amountErrorLabel.setText("Wymagana liczba całkowita powyżej 0.");
            } else {
                amountErrorLabel.setText("");
            }
        }

        if(!isNameEmpty && !isNameError && !isDescriptionEmpty && !isPriceEmpty &&
                !isPriceError && !isDiscountError && !isAmountEmpty && !isAmountError) {
            Product product = new Product();

            if(isUpdateFlag) {
                product.setId(Long.parseLong(productIdLabel.getText()));
            }

            product.setName(nameTextField.getText());
            product.setDescription(descriptionTextField.getText());
            product.setPrice(new BigDecimal(priceTextField.getText()));
            product.setAmount(Integer.parseInt(amountTextField.getText()));
            product.setDiscount(Integer.parseInt(discountTextField.getText()));

            productRepository.save(product);
            mainWindowController.refreshProductTableView();

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize() {
        isNameEmpty = false;
        isNameError = false;

        isDescriptionEmpty = false;

        isPriceEmpty = false;
        isPriceError = false;

        isDiscountError = false;

        isAmountEmpty = false;
        isAmountError = false;

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
    }

    public void setTextFields(Product product) {
        productIdLabel.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        descriptionTextField.setText(product.getDescription());
        priceTextField.setText(String.valueOf(product.getPrice()));
        amountTextField.setText(String.valueOf(product.getAmount()));
        discountTextField.setText(String.valueOf(product.getDiscount()));
    }
}
