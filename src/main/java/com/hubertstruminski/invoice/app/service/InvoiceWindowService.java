package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.component.InvoiceDetailsWindowComponent;
import com.hubertstruminski.invoice.app.component.NewInvoiceWindowComponent;
import com.hubertstruminski.invoice.app.controller.InvoiceDetailsWindowController;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.controller.NewInvoiceWindowController;
import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.InvoiceRepository;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import moe.tristan.easyfxml.EasyFxml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceWindowService implements CoreServiceInterface {

    private final InvoiceRepository invoiceRepository;
    private final MainWindowController mainWindowController;
    private final EasyFxml easyFxml;
    private final NewInvoiceWindowComponent newInvoiceWindowComponent;
    private final CoreService coreService;
    private final NewInvoiceWindowController newInvoiceWindowController;
    private final ProductRepository productRepository;
    private final InvoiceDetailsWindowComponent invoiceDetailsWindowComponent;
    private final InvoiceDetailsWindowController invoiceDetailsWindowController;

    @Autowired
    public InvoiceWindowService(
            InvoiceRepository invoiceRepository,
            MainWindowController mainWindowController,
            EasyFxml easyFxml,
            NewInvoiceWindowComponent newInvoiceWindowComponent,
            CoreService coreService,
            NewInvoiceWindowController newInvoiceWindowController,
            InvoiceDetailsWindowComponent invoiceDetailsWindowComponent,
            InvoiceDetailsWindowController invoiceDetailsWindowController,
            ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.mainWindowController = mainWindowController;
        this.easyFxml = easyFxml;
        this.newInvoiceWindowComponent = newInvoiceWindowComponent;
        this.coreService = coreService;
        this.newInvoiceWindowController = newInvoiceWindowController;
        this.productRepository = productRepository;
        this.invoiceDetailsWindowComponent = invoiceDetailsWindowComponent;
        this.invoiceDetailsWindowController = invoiceDetailsWindowController;
    }

    @Override
    public void setDataForTableView(TableView tableView) {
        Iterable<Invoice> invoicesIterable = invoiceRepository.findAll();
        ObservableList<Invoice> observableTaxList = FXCollections.observableArrayList();
        invoicesIterable.forEach(observableTaxList::add);

        List<Invoice> invoices = new ArrayList<>();
        invoicesIterable.forEach(invoices::add);

        invoices.stream().forEach(invoice -> {
            List<Product> allByInvoice = productRepository.findAllByInvoice(invoice);
            invoice.setProducts(allByInvoice);
        });

        tableView.setItems(observableTaxList);
    }

    public TableColumn setOnClickEditDeleteAction(TableColumn tableColumn, String actionString, boolean isUpdating,
                                                  String styles) {
        Callback<TableColumn<Invoice, String>, TableCell<Invoice, String>> callback
                =
                param -> new TableCell<>() {
                    final Button btn = new Button(actionString);

                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                Invoice invoice = getTableView().getItems().get(getIndex());
                                if (isUpdating) {
                                    invokeNewInvoiceWindowForUpdateInvoice();
                                    newInvoiceWindowController.setTextFields(invoice);
                                    newInvoiceWindowController.setUpdateFlag(true);
                                } else {
                                    List<Product> allByInvoice = productRepository.findAllByInvoice(invoice);
                                    allByInvoice.stream().forEach(x -> x.setInvoice(null));
                                    allByInvoice.stream().forEach(productRepository::save);
                                    invoiceRepository.delete(invoice);
                                    mainWindowController.refreshInvoiceTableView();
                                }
                            });
                            setGraphic(coreService.getBtn(btn, styles));
                        }
                    }
                };
        tableColumn.setCellFactory(callback);
        return tableColumn;
    }

    public void setOnClickEventHandlerForRowInInvoicesTable(TableView tableView, VBox rightVBoxView) {
        tableView.setRowFactory(tv -> {
            TableRow<Invoice> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Invoice clickedRow = row.getItem();

                    easyFxml.load(invoiceDetailsWindowComponent)
                            .afterNodeLoaded(pane -> {
                                VBox vBox = (VBox) pane;
                                vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                                rightVBoxView.getChildren().setAll(vBox);

                                invoiceDetailsWindowController.setTextFields(clickedRow);
                            });
                }
            });
            return row;
        });
    }

    public void invokeNewInvoiceWindowForUpdateInvoice() {
        easyFxml.load(newInvoiceWindowComponent)
                .afterNodeLoaded(pane -> {
                    Scene scene = new Scene(pane, 400, 500);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);

                    stage.show();
                });
    }
}
