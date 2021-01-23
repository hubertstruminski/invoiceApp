package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.component.CustomerDetailsWindowComponent;
import com.hubertstruminski.invoice.app.component.NewCustomerWindowComponent;
import com.hubertstruminski.invoice.app.controller.CustomerDetailsWindowController;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.controller.NewCustomerWindowController;
import com.hubertstruminski.invoice.app.model.Address;
import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.repository.AddressRepository;
import com.hubertstruminski.invoice.app.repository.CustomerRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import moe.tristan.easyfxml.EasyFxml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerWindowService implements CoreServiceInterface {

    private final CustomerRepository customerRepository;
    private final EasyFxml easyFxml;
    private final NewCustomerWindowComponent newCustomerWindowComponent;
    private final NewCustomerWindowController newCustomerWindowController;
    private final AddressRepository addressRepository;
    private final MainWindowController mainWindowController;
    private final CustomerDetailsWindowComponent customerDetailsWindowComponent;
    private final CustomerDetailsWindowController customerDetailsWindowController;
    private final CoreService coreService;

    @Autowired
    public CustomerWindowService(
            CustomerRepository customerRepository,
            EasyFxml easyFxml,
            NewCustomerWindowController newCustomerWindowController,
            AddressRepository addressRepository,
            MainWindowController mainWindowController,
            CustomerDetailsWindowComponent customerDetailsWindowComponent,
            CustomerDetailsWindowController customerDetailsWindowController,
            CoreService coreService,
            NewCustomerWindowComponent newCustomerWindowComponent) {
        this.customerRepository = customerRepository;
        this.easyFxml = easyFxml;
        this.newCustomerWindowComponent = newCustomerWindowComponent;
        this.newCustomerWindowController = newCustomerWindowController;
        this.addressRepository = addressRepository;
        this.mainWindowController = mainWindowController;
        this.customerDetailsWindowComponent = customerDetailsWindowComponent;
        this.customerDetailsWindowController = customerDetailsWindowController;
        this.coreService = coreService;
    }

    public void setDataForTableView(TableView tableView) {
        Iterable<Customer> customerIterable = customerRepository.findAll();
        ObservableList<Customer> observableTaxList = FXCollections.observableArrayList();
        customerIterable.forEach(observableTaxList::add);

        tableView.setItems(observableTaxList);
    }

    public void updateAndRefresh(Customer customer, Address address) {
        coreService.invokeNewItemWindow(newCustomerWindowComponent);
        newCustomerWindowController.setTextFields(customer);
        newCustomerWindowController.setUpdateFlag(true);
        newCustomerWindowController.setUpdateFlagWithTableView(true);
        newCustomerWindowController.setCustomerAddress(address);
    }

    public void deleteAndRefresh(Customer customer, Address address) {
        customerRepository.delete(customer);
        addressRepository.delete(address);
        mainWindowController.refreshCustomerTableView();
    }

    public TableColumn setOnClickEditDeleteAction(TableColumn tableColumn, String actionString, boolean isUpdating,
                                                  String styles) {
        Callback<TableColumn<Customer, String>, TableCell<Customer, String>> callback
                =
                param -> new TableCell<>() {
                    final Button btn = new Button(actionString);

                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                Customer customer = getTableView().getItems().get(getIndex());
                                Address address = customer.getAddress();
                                if (isUpdating) {
                                    updateAndRefresh(customer, address);
                                } else {
                                    deleteAndRefresh(customer, address);
                                }
                            });
                            setGraphic(coreService.getBtn(btn, styles));
                        }
                    }
                };
        tableColumn.setCellFactory(callback);
        return tableColumn;
    }

    public void setOnClickEventHandlerForRowInCustomersTable(TableView tableView, VBox rightVBoxView) {
        tableView.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Customer clickedRow = row.getItem();

                    easyFxml.load(customerDetailsWindowComponent)
                            .afterNodeLoaded(pane -> {
                                VBox vBox = (VBox) pane;
                                vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                                rightVBoxView.getChildren().setAll(vBox);

                                customerDetailsWindowController.setTextFields(clickedRow);
                            });
                }
            });
            return row;
        });
    }
}
