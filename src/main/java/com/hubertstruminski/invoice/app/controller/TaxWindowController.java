package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.service.CoreService;
import com.hubertstruminski.invoice.app.service.TaxWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TaxWindowController implements FxmlController {

    private final TaxWindowService taxWindowService;
    private final CoreService coreService;

    @Autowired
    public TaxWindowController(
            TaxWindowService taxWindowService,
            CoreService coreService) {
        this.taxWindowService = taxWindowService;
        this.coreService = coreService;
    }

    @FXML
    private TableView<Tax> tableView;

    @FXML
    private TableColumn<Tax, String> numberTableColumn;

    @FXML
    private TableColumn<Tax, String> nameTableColumn;

    @FXML
    private TableColumn<Tax, String> descriptionTableColumn;

    @FXML
    private TableColumn<Tax, String> taxAmountTableColumn;

    @FXML
    private TableColumn<Tax, String> editTableColumn;

    @FXML
    private TableColumn<Tax, String> deleteTableColumn;

    @Override
    public void initialize() {
        numberTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        numberTableColumn.setResizable(true);

        nameTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        nameTableColumn.setResizable(true);

        descriptionTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        descriptionTableColumn.setResizable(true);

        taxAmountTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        taxAmountTableColumn.setResizable(true);

        editTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        editTableColumn.setResizable(false);

        coreService.setColumnsAndDataInTableView(deleteTableColumn, nameTableColumn, tableView, taxWindowService,
                0.1, "name");

        numberTableColumn.setCellValueFactory(parameter ->
                new ReadOnlyObjectWrapper(tableView.getItems().indexOf(parameter.getValue()) + 1));
        numberTableColumn.setSortable(false);

        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        taxAmountTableColumn.setCellValueFactory(new PropertyValueFactory<>("taxAmount"));

        editTableColumn = taxWindowService.setOnClickEditDeleteAction(editTableColumn,
                "Edytuj",
                true,
                Constants.WARNING_BUTTON_STYLES);
        deleteTableColumn = taxWindowService.setOnClickEditDeleteAction(deleteTableColumn,
                "Usuń",
                false,
                Constants.DANGER_BUTTON_STYLES);

        tableView.getColumns().setAll(
                numberTableColumn,
                nameTableColumn,
                taxAmountTableColumn,
                descriptionTableColumn,
                editTableColumn,
                deleteTableColumn);
    }
}
