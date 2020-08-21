package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.repository.CustomerRepository;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChooseCustomerWindowController implements FxmlController {

    private final CustomerRepository customerRepository;
    private final NewInvoiceWindowController newInvoiceWindowController;

    @Autowired
    public ChooseCustomerWindowController(
            CustomerRepository customerRepository,
            NewInvoiceWindowController newInvoiceWindowController) {
        this.customerRepository = customerRepository;
        this.newInvoiceWindowController = newInvoiceWindowController;
    }

    @FXML
    private ScrollPane scrollPane;

    @Override
    public void initialize() {
        VBox mainVBox = new VBox();

        scrollPane.setFitToWidth(true);
        scrollPane.setContent(mainVBox);

        Iterable<Customer> customersIterable = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        customersIterable.forEach(customers::add);

        for(Customer customer: customers) {

            GridPane gridPane = new GridPane();

            Label label = new Label(customer.getName());
            gridPane.add(label, 0, 0);

            gridPane.add(new Label(customer.getAddress().getAddress() + " " + customer.getAddress().getCountry()), 0, 1);
            gridPane.add(new Label(customer.getPhoneNumber()), 0 ,2);

            VBox vBox = new VBox(gridPane);
            vBox.setFillWidth(true);

            vBox.setMaxWidth(400);
            vBox.setMinWidth(400);

            vBox.setStyle(Constants.CHOOSE_INTERFACE_ITEMS);

            vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                newInvoiceWindowController.setCustomer(customer);
                Stage stage = (Stage) scrollPane.getScene().getWindow();
                stage.close();
            });
            mainVBox.getChildren().add(vBox);
        }
    }
}
