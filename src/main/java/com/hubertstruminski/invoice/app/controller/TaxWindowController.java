package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.model.Tax;
import com.hubertstruminski.invoice.app.repository.TaxRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;

import java.util.function.Consumer;

@Controller
public class TaxWindowController implements FxmlController {

    private static final String warningButtonStyles = "-fx-background-color: #FFC108; -fx-text-fill: #212529;";
    private static final String dangerButtonStyles = "-fx-background-color: #DC3545; -fx-text-fill: white;";

    private TaxRepository taxRepository;
    private NewTaxWindowComponent newTaxWindowComponent;
    private EasyFxml easyFxml;
    private NewTaxWindowController newTaxWindowController;
    private MainWindowController mainWindowController;

    @Autowired
    public TaxWindowController(
            TaxRepository taxRepository,
            NewTaxWindowComponent newTaxWindowComponent,
            EasyFxml easyFxml,
            NewTaxWindowController newTaxWindowController,
            MainWindowController mainWindowController) {
        this.taxRepository = taxRepository;
        this.newTaxWindowComponent = newTaxWindowComponent;
        this.easyFxml = easyFxml;
        this.newTaxWindowController = newTaxWindowController;
        this.mainWindowController = mainWindowController;
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
        numberTableColumn.setResizable(false);

        nameTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        nameTableColumn.setResizable(false);

        descriptionTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.4));
        descriptionTableColumn.setResizable(false);

        taxAmountTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05));
        taxAmountTableColumn.setResizable(false);

        editTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        editTableColumn.setResizable(false);

        deleteTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        deleteTableColumn.setResizable(false);

        Iterable<Tax> taxIterable = taxRepository.findAll();
        ObservableList<Tax> observableTaxList = FXCollections.observableArrayList();
        taxIterable.forEach(observableTaxList::add);


        tableView.setItems(observableTaxList);

        numberTableColumn.setCellValueFactory(parameter ->
                new ReadOnlyObjectWrapper(tableView.getItems().indexOf(parameter.getValue()) + 1));
        numberTableColumn.setSortable(false);

        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        taxAmountTableColumn.setCellValueFactory(new PropertyValueFactory<>("taxAmount"));

        editTableColumn = setOnClickEditDeleteAction(editTableColumn,
                "Edytuj",
                true,
                warningButtonStyles);
        deleteTableColumn = setOnClickEditDeleteAction(deleteTableColumn,
                "Usuń",
                false,
                dangerButtonStyles);

        tableView.getColumns().setAll(
                numberTableColumn,
                nameTableColumn,
                taxAmountTableColumn,
                descriptionTableColumn,
                editTableColumn,
                deleteTableColumn);
    }

    public TableColumn setOnClickEditDeleteAction(TableColumn tableColumn, String actionString, boolean isUpdating,
                                                  String styles) {
        Callback<TableColumn<Tax, String>, TableCell<Tax, String>> callback
                =
                new Callback<>() {
                    @Override
                    public TableCell call(final TableColumn<Tax, String> param) {
                        final TableCell<Tax, String> cell = new TableCell<Tax, String>() {
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
                                        Tax tax = getTableView().getItems().get(getIndex());
                                        if(isUpdating) {
                                            invokeNewTaxWindowForUpdateTax();
                                            newTaxWindowController.setTextFields(tax);
                                            newTaxWindowController.setUpdateFlag(true);
                                        } else {
                                            taxRepository.delete(tax);
                                            mainWindowController.refreshTaxTableView();
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

    public void invokeNewTaxWindowForUpdateTax() {
        easyFxml.load(newTaxWindowComponent)
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
