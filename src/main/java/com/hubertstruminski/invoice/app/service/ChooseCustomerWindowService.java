package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.controller.NewInvoiceWindowController;
import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.repository.CustomerRepository;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChooseCustomerWindowService {

    private final NewInvoiceWindowController newInvoiceWindowController;
    private final CustomerRepository customerRepository;

    @Autowired
    public ChooseCustomerWindowService(
            NewInvoiceWindowController newInvoiceWindowController,
            CustomerRepository customerRepository) {
        this.newInvoiceWindowController = newInvoiceWindowController;
        this.customerRepository = customerRepository;
    }

    public void addEventHandlerForChooseCustomer(VBox vBox, Customer customer, ScrollPane scrollPane) {
        vBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            newInvoiceWindowController.setCustomer(customer);
            Stage stage = (Stage) scrollPane.getScene().getWindow();
            stage.close();
        });
    }

    public List<Customer> findCustomers() {
        Iterable<Customer> customersIterable = customerRepository.findAll();
        List<Customer> customers = new ArrayList<>();
        customersIterable.forEach(customers::add);
        return customers;
    }

    public GridPane createGridPaneStructure(Customer customer) {
        GridPane gridPane = new GridPane();

        Label label = new Label(customer.getName());
        gridPane.add(label, 0, 0);

        gridPane.add(new Label(customer.getAddress().getAddress() + " " + customer.getAddress().getCountry()), 0, 1);
        gridPane.add(new Label(customer.getPhoneNumber()), 0 ,2);
        return gridPane;
    }
}
