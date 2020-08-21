package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.component.NewProductWindowComponent;
import com.hubertstruminski.invoice.app.component.ProductDetailsWindowComponent;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.controller.NewProductWindowController;
import com.hubertstruminski.invoice.app.controller.ProductDetailsWindowController;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
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
public class ProductWindowService implements CoreServiceInterface {

    private final ProductRepository productRepository;
    private final CoreService coreService;
    private final EasyFxml easyFxml;
    private final NewProductWindowComponent newProductWindowComponent;
    private final NewProductWindowController newProductWindowController;
    private final MainWindowController mainWindowController;
    private final ProductDetailsWindowComponent productDetailsWindowComponent;
    private final ProductDetailsWindowController productDetailsWindowController;

    @Autowired
    public ProductWindowService(
            ProductRepository productRepository,
            CoreService coreService,
            EasyFxml easyFxml,
            NewProductWindowComponent newProductWindowComponent,
            NewProductWindowController newProductWindowController,
            MainWindowController mainWindowController,
            ProductDetailsWindowComponent productDetailsWindowComponent,
            ProductDetailsWindowController productDetailsWindowController) {
        this.productRepository = productRepository;
        this.coreService = coreService;
        this.easyFxml = easyFxml;
        this.newProductWindowComponent = newProductWindowComponent;
        this.newProductWindowController = newProductWindowController;
        this.mainWindowController = mainWindowController;
        this.productDetailsWindowComponent = productDetailsWindowComponent;
        this.productDetailsWindowController = productDetailsWindowController;

    }

    @Override
    public void setDataForTableView(TableView tableView) {
        Iterable<Product> customerIterable = productRepository.findAll();
        ObservableList<Product> observableTaxList = FXCollections.observableArrayList();
        customerIterable.forEach(observableTaxList::add);

        tableView.setItems(observableTaxList);
    }

    public TableColumn setOnClickEditDeleteAction(TableColumn tableColumn, String actionString, boolean isUpdating,
                                                  String styles) {
        Callback<TableColumn<Product, String>, TableCell<Product, String>> callback
                =
                param -> new TableCell<>() {
                    final Button btn = new Button(actionString);

                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                Product product = getTableView().getItems().get(getIndex());
                                if (isUpdating) {
                                    coreService.invokeNewItemWindow(newProductWindowComponent);
                                    newProductWindowController.setTextFields(product);
                                    newProductWindowController.setTax(product.getTax());
                                    newProductWindowController.setUpdateFlag(true);
                                } else {
                                    productRepository.delete(product);
                                    mainWindowController.refreshProductTableView();
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
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Product clickedRow = row.getItem();

                    easyFxml.load(productDetailsWindowComponent)
                            .afterNodeLoaded(pane -> {
                                VBox vBox = (VBox) pane;
                                vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                                rightVBoxView.getChildren().setAll(vBox);

                                productDetailsWindowController.setTextFields(clickedRow);
                            });
                }
            });
            return row;
        });
    }
}
