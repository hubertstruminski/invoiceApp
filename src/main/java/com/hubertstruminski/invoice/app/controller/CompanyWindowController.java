package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.CompanyDetailsWindowComponent;
import com.hubertstruminski.invoice.app.model.Company;
import com.hubertstruminski.invoice.app.service.CompanyWindowService;
import com.hubertstruminski.invoice.app.service.CoreService;
import com.hubertstruminski.invoice.app.service.MainWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

@Controller
public class CompanyWindowController implements FxmlController {

    private final CoreService coreService;
    private final CompanyWindowService companyWindowService;
    private final MainWindowService mainWindowService;
    private final CompanyDetailsWindowComponent companyDetailsWindowComponent;

    @Autowired
    public CompanyWindowController(
            CoreService coreService,
            CompanyWindowService companyWindowService,
            MainWindowService mainWindowService,
            CompanyDetailsWindowComponent companyDetailsWindowComponent) {
        this.coreService = coreService;
        this.companyWindowService = companyWindowService;
        this.mainWindowService = mainWindowService;
        this.companyDetailsWindowComponent = companyDetailsWindowComponent;
    }

    @FXML
    private TableView<Company> tableView;

    @FXML
    private TableColumn<Company, String> numberTableColumn;

    @FXML
    private TableColumn<Company, String> nameTableColumn;

    @FXML
    private TableColumn<Company, String> editTableColumn;

    @FXML
    private TableColumn<Company, String> deleteTableColumn;

    @FXML
    private VBox rightVBoxView;

    @Override
    public void initialize() {
        numberTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        numberTableColumn.setResizable(true);

        nameTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.5));
        nameTableColumn.setResizable(true);

        editTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        editTableColumn.setResizable(false);

        coreService.setColumnsAndDataInTableView(deleteTableColumn, nameTableColumn, tableView, companyWindowService,
                0.2, "companyName");

        numberTableColumn.setCellValueFactory(parameter ->
                new ReadOnlyObjectWrapper(tableView.getItems().indexOf(parameter.getValue()) + 1));
        numberTableColumn.setSortable(false);

        editTableColumn = companyWindowService.setOnClickEditDeleteAction(editTableColumn,
                "Edytuj",
                true,
                Constants.WARNING_BUTTON_STYLES);
        deleteTableColumn = companyWindowService.setOnClickEditDeleteAction(deleteTableColumn,
                "Usuń",
                false,
                Constants.DANGER_BUTTON_STYLES);

        companyWindowService.setOnClickEventHandlerForRowInCompaniesTable(tableView, rightVBoxView);

        tableView.getColumns().setAll(
                numberTableColumn,
                nameTableColumn,
                editTableColumn,
                deleteTableColumn);
    }

    public void onSubViewChange() {
        mainWindowService.refreshSubView(companyDetailsWindowComponent, rightVBoxView);
    }
}
