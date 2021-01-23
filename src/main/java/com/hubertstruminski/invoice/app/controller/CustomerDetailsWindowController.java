package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.repository.InvoiceRepository;
import com.hubertstruminski.invoice.app.service.CustomerDetailsWindowService;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

@Controller
public class CustomerDetailsWindowController implements FxmlController {

    private final InvoiceRepository invoiceRepository;
    private final CustomerDetailsWindowService customerDetailsWindowService;

    @Autowired
    public CustomerDetailsWindowController(
            InvoiceRepository invoiceRepository,
            CustomerDetailsWindowService customerDetailsWindowService) {
        this.invoiceRepository = invoiceRepository;
        this.customerDetailsWindowService = customerDetailsWindowService;
    }

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailValueLabel;

    @FXML
    private Label phoneNumberValueLabel;

    @FXML
    private Label nipValueLabel;

    @FXML
    private Label addressValueLabel;

    @FXML
    private Label countryValueLabel;

    @FXML
    private Label websiteValueLabel;

    @FXML
    private Label commentValueLabel;

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {
    }

    public void setTextFields(Customer customer) {
        nameLabel.setText(customer.getName());
        emailValueLabel.setText(customer.getEmail());
        phoneNumberValueLabel.setText(customer.getPhoneNumber());
        nipValueLabel.setText(customer.getNip());
        addressValueLabel.setText(customer.getAddress().getAddress());
        countryValueLabel.setText(customer.getAddress().getCountry());
        websiteValueLabel.setText(customer.getWebsite());
        commentValueLabel.setText(customer.getNote());

        List<Invoice> invoices = invoiceRepository.findAllByCustomer(customer);

        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        for(Invoice invoice: invoices) {
            GridPane gridPane = new GridPane();
            customerDetailsWindowService.setListColumnConstraints(gridPane);
            customerDetailsWindowService.setGridPaneLayout(gridPane, invoice);
            Button sendButton = customerDetailsWindowService.setGridPaneSendButtonLayout(gridPane);
            customerDetailsWindowService.addEventHandlerForSendButton(sendButton, invoice);

            customerDetailsWindowService.setGridPanePdfButtonLayout(gridPane, invoice);
            customerDetailsWindowService.setGridPaneStatusLayout(gridPane, invoice);
            VBox vBox = customerDetailsWindowService.createVBox(gridPane);

            mainVBox.getChildren().add(vBox);
        }
    }
}
