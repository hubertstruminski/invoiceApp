package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.service.CoreService;
import com.hubertstruminski.invoice.app.service.CustomerWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerWindowController implements FxmlController {

    private final CustomerWindowService customerWindowService;
    private final CoreService coreService;

    @Autowired
    public CustomerWindowController(
            CustomerWindowService customerWindowService,
            CoreService coreService) {
        this.customerWindowService = customerWindowService;
        this.coreService = coreService;
    }

    @FXML
    private TableView<Customer> tableView;

    @FXML
    private TableColumn<Customer, String> numberTableColumn;

    @FXML
    private TableColumn<Customer, String> nameTableColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberTableColumn;

    @FXML
    private TableColumn<Customer, String> emailTableColumn;

    @FXML
    private TableColumn<Customer, String> editTableColumn;

    @FXML
    private TableColumn<Customer, String> deleteTableColumn;

    @FXML
    private VBox rightVBoxView;

    @Override
    public void initialize() {
        numberTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        numberTableColumn.setResizable(false);

        nameTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));
        nameTableColumn.setResizable(false);

        phoneNumberTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.225));
        phoneNumberTableColumn.setResizable(false);

        emailTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.225));
        emailTableColumn.setResizable(false);

        editTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        editTableColumn.setResizable(false);

        coreService.setColumnsAndDataInTableView(deleteTableColumn, nameTableColumn, tableView, customerWindowService);

        numberTableColumn.setCellValueFactory(parameter ->
                new ReadOnlyObjectWrapper(tableView.getItems().indexOf(parameter.getValue()) + 1));
        numberTableColumn.setSortable(false);

        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        editTableColumn = customerWindowService.setOnClickEditDeleteAction(editTableColumn,
                "Edytuj",
                true,
                Constants.WARNING_BUTTON_STYLES);
        deleteTableColumn = customerWindowService.setOnClickEditDeleteAction(deleteTableColumn,
                "Usuń",
                false,
                Constants.DANGER_BUTTON_STYLES);

        customerWindowService.setOnClickEventHandlerForRowInCustomersTable(tableView, rightVBoxView);

        tableView.getColumns().setAll(
                numberTableColumn,
                nameTableColumn,
                phoneNumberTableColumn,
                emailTableColumn,
                editTableColumn,
                deleteTableColumn);
    }
}
