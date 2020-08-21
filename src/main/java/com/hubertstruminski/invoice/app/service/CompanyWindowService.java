package com.hubertstruminski.invoice.app.service;

import com.hubertstruminski.invoice.app.component.CompanyDetailsWindowComponent;
import com.hubertstruminski.invoice.app.component.NewCompanyWindowComponent;
import com.hubertstruminski.invoice.app.controller.CompanyDetailsWindowController;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import com.hubertstruminski.invoice.app.controller.NewCompanyWindowController;
import com.hubertstruminski.invoice.app.model.Company;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.repository.CompanyRepository;
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

@Service
public class CompanyWindowService implements CoreServiceInterface {

    private final CoreService coreService;
    private final CompanyRepository companyRepository;
    private final EasyFxml easyFxml;
    private final NewCompanyWindowComponent newCompanyWindowComponent;
    private final NewCompanyWindowController newCompanyWindowController;
    private final MainWindowController mainWindowController;
    private final CompanyDetailsWindowComponent companyDetailsWindowComponent;
    private final CompanyDetailsWindowController companyDetailsWindowController;

    @Autowired
    public CompanyWindowService(
            CompanyRepository companyRepository,
            CoreService coreService,
            EasyFxml easyFxml,
            NewCompanyWindowComponent newCompanyWindowComponent,
            MainWindowController mainWindowController,
            NewCompanyWindowController newCompanyWindowController,
            CompanyDetailsWindowComponent companyDetailsWindowComponent,
            CompanyDetailsWindowController companyDetailsWindowController) {
        this.companyRepository = companyRepository;
        this.coreService = coreService;
        this.easyFxml = easyFxml;
        this.newCompanyWindowComponent = newCompanyWindowComponent;
        this.mainWindowController = mainWindowController;
        this.newCompanyWindowController = newCompanyWindowController;
        this.companyDetailsWindowComponent = companyDetailsWindowComponent;
        this.companyDetailsWindowController = companyDetailsWindowController;
    }

    @Override
    public void setDataForTableView(TableView tableView) {
        Iterable<Company> companyIterable = companyRepository.findAll();
        ObservableList<Company> observableTaxList = FXCollections.observableArrayList();
        companyIterable.forEach(observableTaxList::add);

        tableView.setItems(observableTaxList);
    }

    public TableColumn setOnClickEditDeleteAction(TableColumn tableColumn, String actionString, boolean isUpdating,
                                                  String styles) {
        Callback<TableColumn<Company, String>, TableCell<Company, String>> callback
                =
                param -> new TableCell<>() {
                    final Button btn = new Button(actionString);

                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                Company company = getTableView().getItems().get(getIndex());
                                if (isUpdating) {
                                    invokeNewCompanyWindowForUpdateCompany();
                                    newCompanyWindowController.setTextFields(company);
                                    newCompanyWindowController.setUpdateFlag(true);
                                } else {
                                    companyRepository.delete(company);
                                    mainWindowController.refreshCompanyTableView();
                                }
                            });
                            setGraphic(coreService.getBtn(btn, styles));
                        }
                    }
                };
        tableColumn.setCellFactory(callback);
        return tableColumn;
    }

    public void invokeNewCompanyWindowForUpdateCompany() {
        easyFxml.load(newCompanyWindowComponent)
                .afterNodeLoaded(pane -> {
                    Scene scene = new Scene(pane, 400, 500);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);

                    stage.show();
                });
    }

    public void setOnClickEventHandlerForRowInCompaniesTable(TableView<Company> tableView, VBox rightVBoxView) {
        tableView.setRowFactory(tv -> {
            TableRow<Company> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Company clickedRow = row.getItem();

                    easyFxml.load(companyDetailsWindowComponent)
                            .afterNodeLoaded(pane -> {
                                VBox vBox = (VBox) pane;
                                vBox.prefHeightProperty().bind(rightVBoxView.getScene().heightProperty());
                                rightVBoxView.getChildren().setAll(vBox);

                                companyDetailsWindowController.setTextFields(clickedRow);
                            });
                }
            });
            return row;
        });
    }
}
