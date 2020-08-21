package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.ChooseCustomerWindowComponent;
import com.hubertstruminski.invoice.app.component.ChooseProductWindowComponent;
import com.hubertstruminski.invoice.app.model.*;
import com.hubertstruminski.invoice.app.repository.CustomerRepository;
import com.hubertstruminski.invoice.app.repository.InvoiceRepository;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.service.MainWindowService;
import com.hubertstruminski.invoice.app.service.NewInvoiceWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class NewInvoiceWindowController implements FxmlController {

    private final MainWindowService mainWindowService;
    private final ChooseCustomerWindowComponent chooseCustomerWindowComponent;
    private final ChooseProductWindowComponent chooseProductWindowComponent;
    private final InvoiceRepository invoiceRepository;
    private final MainWindowController mainWindowController;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final NewInvoiceWindowService newInvoiceWindowService;

    private Customer customer = null;
    private List<Product> products = new ArrayList<>();

    private boolean isNumberEmpty = false;
    private boolean isDateEmpty = false;
    private boolean isCustomerEmpty = false;
    private boolean isProductEmpty = false;

    private boolean isUpdateFlag = false;

    @Autowired
    public NewInvoiceWindowController(
            MainWindowService mainWindowService,
            ChooseCustomerWindowComponent chooseCustomerWindowComponent,
            ChooseProductWindowComponent chooseProductWindowComponent,
            InvoiceRepository invoiceRepository,
            MainWindowController mainWindowController,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            NewInvoiceWindowService newInvoiceWindowService) {
        this.mainWindowService = mainWindowService;
        this.chooseCustomerWindowComponent = chooseCustomerWindowComponent;
        this.chooseProductWindowComponent = chooseProductWindowComponent;
        this.invoiceRepository = invoiceRepository;
        this.mainWindowController = mainWindowController;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.newInvoiceWindowService = newInvoiceWindowService;
    }

    @FXML
    private VBox vBox;

    @FXML
    private TextField numberTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button addProductButton;

    @FXML
    private TextField commentTextField;

    @FXML
    private Label numberErrorLabel;

    @FXML
    private Label dateErrorLabel;

    @FXML
    private Label customerErrorLabel;

    @FXML
    private Label productErrorLabel;

    @FXML
    private Label invoiceIdLabel;

    @FXML
    private Button listProductsButton;

    public void setUpdateFlag(boolean updateFlag) {
        isUpdateFlag = updateFlag;
    }

    @FXML
    void onAddCustomerButtonAction() {
        mainWindowService.onLoadComponent(
                chooseCustomerWindowComponent,
                400,
                350,
                false,
                "Wybierz klienta");
    }

    @FXML
    void onAddProductButtonAction() {
        mainWindowService.onLoadComponent(
                chooseProductWindowComponent,
                400,
                350,
                false,
                "Wybierz produkt");
    }

    @FXML
    void onAddProductClearButton() {
        if(isUpdateFlag) {
            Optional<Invoice> optionalInvoiceById = invoiceRepository.findById(Long.valueOf(invoiceIdLabel.getText()));
            Invoice invoiceById = optionalInvoiceById.get();

            List<Product> processedProducts = invoiceById.getProducts();
            processedProducts.forEach(x -> x.setInvoice(null));
            processedProducts.forEach(productRepository::delete);
        }
        products = new ArrayList<>();
        listProductsButton.setText(">");
    }

    @FXML
    void onSaveInvoiceButtonAction() {
        if (numberTextField.getText().length() == 0) isNumberEmpty = true;
        else isNumberEmpty = false;

        if(datePicker.getValue() == null) isDateEmpty = true;
        else isDateEmpty = false;

        if(addCustomerButton.getText().equalsIgnoreCase(">")) isCustomerEmpty = true;
        else isCustomerEmpty = false;

        if(addProductButton.getText().equalsIgnoreCase(">")) isProductEmpty = true;
        else isProductEmpty = false;

        if(isNumberEmpty) {
            numberErrorLabel.setText("Pole numer jest wymagane.");
        } else {
            numberErrorLabel.setText("");
        }

        if(isDateEmpty) {
            dateErrorLabel.setText("Pole data jest wymagane.");
        } else {
            dateErrorLabel.setText("");
        }

        if(isCustomerEmpty) {
            customerErrorLabel.setText("Pole klient jest wymagane.");
        } else {
            customerErrorLabel.setText("");
        }

        if(isProductEmpty) {
            productErrorLabel.setText("Pole produkt jest wymagane.");
        } else {
            productErrorLabel.setText("");
        }

        if(!isNumberEmpty && !isDateEmpty && !isCustomerEmpty && !isProductEmpty) {
            Invoice invoice = new Invoice();

            LocalDate datePickerValue = datePicker.getValue();
            LocalDate deadlinePickerValue = deadlinePicker.getValue();

            if(isUpdateFlag) {
                invoice.setId(Long.parseLong(invoiceIdLabel.getText()));
                Optional<Invoice> optionalInvoiceById = invoiceRepository.findById(invoice.getId());
                Invoice invoiceById = optionalInvoiceById.get();

                customer = invoiceById.getCustomer();
                customerRepository.save(customer);
            }

            invoice.setNumber(numberTextField.getText());
            invoice.setDate(datePickerValue);
            invoice.setDeadline(deadlinePickerValue);
            invoice.setCustomer(customer);
            invoice.setProducts(products);
            invoice.setComment(commentTextField.getText());
            invoice.setStatus(Status.NOT_SENT);

            Invoice savedInvoice = invoiceRepository.save(invoice);


            for(Product product: products) {
                if(product.getInvoice() == null) {
                    product.setInvoice(savedInvoice);
                } else if(product.getInvoice().getId() == invoice.getId()) {
                    continue;
                } else {
                    product.setId(0);
                    product.setInvoice(savedInvoice);
                }
                productRepository.save(product);
            }

            if(!isUpdateFlag) {
                customer.setInvoice(savedInvoice);
                customerRepository.save(customer);
            }
            mainWindowController.refreshInvoiceTableView();

            Stage stage = (Stage) vBox.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize() {
        customer = new Customer();
        products = new ArrayList<>();

        isNumberEmpty = false;
        isDateEmpty = false;
        isCustomerEmpty = false;
        isProductEmpty = false;

        isUpdateFlag = false;

        invoiceIdLabel.setVisible(false);

        numberErrorLabel.setText("");
        numberErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        dateErrorLabel.setText("");
        dateErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        customerErrorLabel.setText("");
        customerErrorLabel.setStyle(Constants.RED_COLOR_FONT);

        productErrorLabel.setText("");
        productErrorLabel.setStyle(Constants.RED_COLOR_FONT);
    }

    public void setCustomer(Customer _customer) {
        customer.setId(_customer.getId());
        customer.setName(_customer.getName());
        customer.setEmail(_customer.getEmail());
        customer.setAddress(_customer.getAddress());
        customer.setPhoneNumber(_customer.getPhoneNumber());
        customer.setWebsite(_customer.getWebsite());
        customer.setNip(_customer.getNip());
        customer.setNote(_customer.getNote());

        addCustomerButton.setText(_customer.getName() + " NIP: " + _customer.getNip());
    }

    public void setTextFields(Invoice invoice) {
        invoiceIdLabel.setText(String.valueOf(invoice.getId()));
        numberTextField.setText(invoice.getNumber());
        datePicker.setValue(invoice.getDate());
        deadlinePicker.setValue(invoice.getDeadline());

        products = invoice.getProducts();
        String productButtonText = newInvoiceWindowService.renderTextForProductButton(products);
        listProductsButton.setText(productButtonText);

        addCustomerButton.setText(invoice.getCustomer().getName() + " NIP: " + invoice.getCustomer().getNip());
        commentTextField.setText(invoice.getComment());
    }

    public void setProduct(Product _product) {
        Product product = new Product();

        product.setId(_product.getId());
        product.setAmount(_product.getAmount());
        product.setPrice(_product.getPrice());
        product.setDescription(_product.getDescription());
        product.setName(_product.getName());
        product.setDiscount(_product.getDiscount());
        product.setInvoice(_product.getInvoice());
        product.setTax(_product.getTax());

        products.add(product);

        String productButtonText = newInvoiceWindowService.renderTextForProductButton(products);
        listProductsButton.setText(productButtonText);
    }

    public List<Product> getChosenProducts() {
        return products;
    }
}
