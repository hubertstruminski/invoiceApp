package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.component.TaxWindowErrorAssignComponent;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.controller.NewTaxWindowController;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.ProductRepository;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import moe.tristan.easyfxml.EasyFxml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxWindowService implements CoreServiceInterface {

    private final TaxRepository taxRepository;
    private final EasyFxml easyFxml;
    private final NewTaxWindowComponent newTaxWindowComponent;
    private final NewTaxWindowController newTaxWindowController;
    private final MainWindowController mainWindowController;
    private final CoreService coreService;
    private final ProductRepository productRepository;
    private final MainWindowService mainWindowService;
    private final TaxWindowErrorAssignComponent taxWindowErrorAssignComponent;

    @Autowired
    public TaxWindowService(
            TaxRepository taxRepository,
            NewTaxWindowComponent newTaxWindowComponent,
            NewTaxWindowController newTaxWindowController,
            MainWindowController mainWindowController,
            EasyFxml easyFxml,
            CoreService coreService,
            ProductRepository productRepository,
            MainWindowService mainWindowService,
            TaxWindowErrorAssignComponent taxWindowErrorAssignComponent) {
        this.taxRepository = taxRepository;
        this.newTaxWindowComponent = newTaxWindowComponent;
        this.easyFxml = easyFxml;
        this.newTaxWindowController = newTaxWindowController;
        this.mainWindowController = mainWindowController;
        this.coreService = coreService;
        this.productRepository = productRepository;
        this.mainWindowService = mainWindowService;
        this.taxWindowErrorAssignComponent = taxWindowErrorAssignComponent;
    }

    public void setDataForTableView(TableView tableView) {
        Iterable<Tax> taxIterable = taxRepository.findAll();
        ObservableList<Tax> observableTaxList = FXCollections.observableArrayList();
        taxIterable.forEach(observableTaxList::add);

        tableView.setItems(observableTaxList);
    }

    public void invokeNewTaxWindowForUpdateTax() {
        easyFxml.load(newTaxWindowComponent)
                .afterNodeLoaded(pane -> {
                    Scene scene = new Scene(pane, 400, 500);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);

                    stage.show();
                });
    }

    public TableColumn setOnClickEditDeleteAction(TableColumn tableColumn, String actionString, boolean isUpdating,
                                                  String styles) {
        Callback<TableColumn<Tax, String>, TableCell<Tax, String>> callback
                =
                param -> new TableCell<>() {
                    final Button btn = new Button(actionString);

                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                Tax tax = getTableView().getItems().get(getIndex());
                                if (isUpdating) {
                                    updateAndRefresh(tax);
                                } else {
                                    List<Product> products = productRepository.findAllByTax(tax);

                                    if(products.size() != 0) {
                                        mainWindowService.onLoadComponent(
                                                taxWindowErrorAssignComponent,
                                                550,
                                                200,
                                                false,
                                                "Ostrzeżenie");
                                    } else {
                                        deleteAndRefresh(tax);
                                    }
                                }
                            });
                            setGraphic(coreService.getBtn(btn, styles));
                        }
                    }
                };
        tableColumn.setCellFactory(callback);
        return tableColumn;
    }

    public void updateAndRefresh(Tax tax) {
        invokeNewTaxWindowForUpdateTax();
        newTaxWindowController.setTextFields(tax);
        newTaxWindowController.setUpdateFlag(true);
    }

    public void deleteAndRefresh(Tax tax) {
        taxRepository.delete(tax);
        mainWindowController.refreshTaxTableView();
    }
}
