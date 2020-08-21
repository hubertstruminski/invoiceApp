package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.model.Customer;
import com.hubertstruminski.invoice.app.model.Invoice;
import com.hubertstruminski.invoice.app.model.Product;
import com.hubertstruminski.invoice.app.service.CoreService;
import com.hubertstruminski.invoice.app.service.CustomerWindowService;
import com.hubertstruminski.invoice.app.service.InvoiceWindowService;
import com.hubertstruminski.invoice.app.util.Constants;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

@Controller
public class InvoiceWindowController implements FxmlController {

    private final CoreService coreService;
    private final InvoiceWindowService invoiceWindowService;

    @Autowired
    public InvoiceWindowController(
            CoreService coreService,
            InvoiceWindowService invoiceWindowService) {
        this.coreService = coreService;
        this.invoiceWindowService = invoiceWindowService;
    }

    @FXML
    private TableView<Invoice> tableView;

    @FXML
    private TableColumn<Invoice, String> noTableColumn;

    @FXML
    private TableColumn<Invoice, String> numberInvoiceTableColumn;

    @FXML
    private TableColumn<Invoice, String> deadlineTableColumn;

    @FXML
    private TableColumn<Invoice, String> productTableColumn;

    @FXML
    private TableColumn<Invoice, String> customerTableColumn;

    @FXML
    private TableColumn<Invoice, String> editTableColumn;

    @FXML
    private TableColumn<Invoice, String> deleteTableColumn;

    @FXML
    private VBox rightVBoxView;

    @Override
    public void initialize() {
        noTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        noTableColumn.setResizable(true);

        numberInvoiceTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        numberInvoiceTableColumn.setResizable(true);

        deadlineTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        deadlineTableColumn.setResizable(true);

        productTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.25));
        productTableColumn.setResizable(true);

        customerTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.20));
        customerTableColumn.setResizable(true);

        editTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15));
        editTableColumn.setResizable(false);

        coreService.setColumnsAndDataInTableViewForInvoices(deleteTableColumn, tableView, invoiceWindowService);

        noTableColumn.setCellValueFactory(parameter ->
                new ReadOnlyObjectWrapper(tableView.getItems().indexOf(parameter.getValue()) + 1));
        noTableColumn.setSortable(false);

        numberInvoiceTableColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        deadlineTableColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));

        productTableColumn.setCellValueFactory(( TableColumn.CellDataFeatures<Invoice, String> item) ->
                {
                    List<Product> products = item.getValue().getProducts();
                    String name = products
                            .stream()
                            .map(x -> x.getName() )
                            .reduce( "", ( acc, x ) -> x + ", " + acc );
                    return new ReadOnlyStringWrapper(name);
                });
        customerTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomer().getName()));

        editTableColumn = invoiceWindowService.setOnClickEditDeleteAction(editTableColumn,
                "Edytuj",
                true,
                Constants.WARNING_BUTTON_STYLES);
        deleteTableColumn = invoiceWindowService.setOnClickEditDeleteAction(deleteTableColumn,
                "Usuń",
                false,
                Constants.DANGER_BUTTON_STYLES);

        invoiceWindowService.setOnClickEventHandlerForRowInInvoicesTable(tableView, rightVBoxView);

        tableView.getColumns().setAll(
                noTableColumn,
                numberInvoiceTableColumn,
                deadlineTableColumn,
                productTableColumn,
                customerTableColumn,
                editTableColumn,
                deleteTableColumn);
    }
}
