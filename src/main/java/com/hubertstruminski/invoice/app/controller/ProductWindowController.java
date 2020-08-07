package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.service.CoreService;
import com.hubertstruminski.invoice.app.service.ProductWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.cell.PropertyValueFactory;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

@Controller
public class ProductWindowController implements FxmlController {

    private final ProductWindowService productWindowService;
    private final CoreService coreService;

    @Autowired
    public ProductWindowController(
            CoreService coreService,
            ProductWindowService productWindowService) {
        this.coreService = coreService;
        this.productWindowService = productWindowService;
    }

    @FXML
    private TableView<Product> tableView;

    @FXML
    private TableColumn<Product, String> numberTableColumn;

    @FXML
    private TableColumn<Product, String> nameTableColumn;

    @FXML
    private TableColumn<Product, String> priceTableColumn;

    @FXML
    private TableColumn<Product, String> amountTableColumn;

    @FXML
    private TableColumn<Product, String> editTableColumn;

    @FXML
    private TableColumn<Product, String> deleteTableColumn;

    @FXML
    private VBox rightVBoxView;

    @Override
    public void initialize() {
        numberTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        numberTableColumn.setResizable(false);

        nameTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.35));
        nameTableColumn.setResizable(false);

        priceTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.20));
        priceTableColumn.setResizable(false);

        amountTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        amountTableColumn.setResizable(false);

        editTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        editTableColumn.setResizable(false);

        coreService.setColumnsAndDataInTableView(deleteTableColumn, nameTableColumn, tableView, productWindowService);

        numberTableColumn.setCellValueFactory(parameter ->
                new ReadOnlyObjectWrapper(tableView.getItems().indexOf(parameter.getValue()) + 1));
        numberTableColumn.setSortable(false);

        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        editTableColumn = productWindowService.setOnClickEditDeleteAction(editTableColumn,
                "Edytuj",
                true,
                Constants.WARNING_BUTTON_STYLES);
        deleteTableColumn = productWindowService.setOnClickEditDeleteAction(deleteTableColumn,
                "Usuń",
                false,
                Constants.DANGER_BUTTON_STYLES);

        productWindowService.setOnClickEventHandlerForRowInCustomersTable(tableView, rightVBoxView);

        tableView.getColumns().setAll(
                numberTableColumn,
                nameTableColumn,
                priceTableColumn,
                amountTableColumn,
                editTableColumn,
                deleteTableColumn);
    }
}
