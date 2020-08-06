package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.CustomerDetailsWindowComponent;
import com.hubertstruminski.invoice.app.component.CustomerWindowComponent;
import com.hubertstruminski.invoice.app.component.NewCustomerWindowComponent;
import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.model.Address;
import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.AddressRepository;
import com.hubertstruminski.invoice.app.repository.CustomerRepository;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class CustomerWindowController implements FxmlController {

    private static final String warningButtonStyles = "-fx-background-color: #FFC108; -fx-text-fill: #212529;";
    private static final String dangerButtonStyles = "-fx-background-color: #DC3545; -fx-text-fill: white;";

    private EasyFxml easyFxml;
    private CustomerRepository customerRepository;
    private MainWindowController mainWindowController;
    private NewCustomerWindowComponent newCustomerWindowComponent;
    private NewCustomerWindowController newCustomerWindowController;
    private NewAddressWindowController newAddressWindowController;
    private AddressRepository addressRepository;
    private CustomerDetailsWindowComponent customerDetailsWindowComponent;
    private CustomerDetailsWindowController customerDetailsWindowController;

    @Autowired
    public CustomerWindowController(
            EasyFxml easyFxml,
            CustomerRepository customerRepository,
            MainWindowController mainWindowController,
            NewCustomerWindowComponent newCustomerWindowComponent,
            NewCustomerWindowController newCustomerWindowController,
            NewAddressWindowController newAddressWindowController,
            CustomerDetailsWindowComponent customerDetailsWindowComponent,
            CustomerDetailsWindowController customerDetailsWindowController,
            AddressRepository addressRepository) {
        this.easyFxml = easyFxml;
        this.customerRepository = customerRepository;
        this.mainWindowController = mainWindowController;
        this.newCustomerWindowComponent = newCustomerWindowComponent;
        this.newCustomerWindowController = newCustomerWindowController;
        this.addressRepository = addressRepository;
        this.newAddressWindowController = newAddressWindowController;
        this.customerDetailsWindowComponent = customerDetailsWindowComponent;
        this.customerDetailsWindowController = customerDetailsWindowController;
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

        deleteTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        deleteTableColumn.setResizable(false);

        Iterable<Customer> customerIterable = customerRepository.findAll();
        ObservableList<Customer> observableTaxList = FXCollections.observableArrayList();
        customerIterable.forEach(observableTaxList::add);

        tableView.setItems(observableTaxList);

        numberTableColumn.setCellValueFactory(parameter ->
                new ReadOnlyObjectWrapper(tableView.getItems().indexOf(parameter.getValue()) + 1));
        numberTableColumn.setSortable(false);

        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailTableColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        editTableColumn = setOnClickEditDeleteAction(editTableColumn,
                "Edytuj",
                true,
                warningButtonStyles);
        deleteTableColumn = setOnClickEditDeleteAction(deleteTableColumn,
                "Usuń",
                false,
                dangerButtonStyles);

        tableView.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Customer clickedRow = row.getItem();
                    System.out.println(clickedRow.getAddress());

                    easyFxml.load(customerDetailsWindowComponent)
                            .afterNodeLoaded(new Consumer<Pane>() {
                                @Override
                                public void accept(Pane pane) {
                                    VBox vBox = (VBox) pane;
                                    vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                                    rightVBoxView.getChildren().setAll(vBox);

                                    customerDetailsWindowController.setTextFields(clickedRow);
                                }
                            });
                }
            });
            return row;
        });

        tableView.getColumns().setAll(
                numberTableColumn,
                nameTableColumn,
                phoneNumberTableColumn,
                emailTableColumn,
                editTableColumn,
                deleteTableColumn);
    }

    public TableColumn setOnClickEditDeleteAction(TableColumn tableColumn, String actionString, boolean isUpdating,
                                                  String styles) {
        Callback<TableColumn<Customer, String>, TableCell<Customer, String>> callback
                =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<Customer, String> param) {
                        final TableCell<Customer, String> cell = new TableCell<Customer, String>() {
                            Button btn = new Button(actionString);

                            public Button getBtn() {
                                btn.setStyle(styles);
                                btn.setCursor(Cursor.HAND);
                                return btn;
                            }

                            @Override
                            public void updateItem(String item, boolean empty) {
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Customer customer = getTableView().getItems().get(getIndex());
                                        Address address = customer.getAddress();
                                        if(isUpdating) {
                                            invokeNewTaxWindowForUpdateCustomer();
                                            newCustomerWindowController.setTextFields(customer);
                                            newCustomerWindowController.setUpdateFlag(true);
                                            newCustomerWindowController.setUpdateFlagWithTableView(true);
                                            newCustomerWindowController.setCustomerAddress(address);
                                        } else {
                                            customerRepository.delete(customer);
                                            addressRepository.delete(address);
                                            mainWindowController.refreshCustomerTableView();
                                        }
                                    });
                                    setGraphic(getBtn());
                                }
                            }
                        };
                        return cell;
                    }
                };
        tableColumn.setCellFactory(callback);
        return tableColumn;
    }

    public void invokeNewTaxWindowForUpdateCustomer() {
        easyFxml.load(newCustomerWindowComponent)
                .afterNodeLoaded(new Consumer<Pane>() {
                    @Override
                    public void accept(Pane pane) {
                        Scene scene = new Scene(pane, 400, 500);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);

                        stage.show();
                    }
                });
    }
}
