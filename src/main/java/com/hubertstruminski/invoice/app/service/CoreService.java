package com.hubertstruminski.invoice.app.service;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoreService {

    private final EasyFxml easyFxml;

    @Autowired
    public CoreService(EasyFxml easyFxml) {
        this.easyFxml = easyFxml;
    }

    public void setColumnsAndDataInTableView(
            TableColumn deleteTableColumn,
            TableColumn nameTableColumn,
            TableView tableView,
            CoreServiceInterface coreServiceInterface,
            double width,
            String nameProperty) {

        deleteTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(width));
        deleteTableColumn.setResizable(false);

        coreServiceInterface.setDataForTableView(tableView);
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>(nameProperty));
    }

    public Button getBtn(Button btn, String styles) {
        btn.setStyle(styles);
        btn.setCursor(Cursor.HAND);
        return btn;
    }

    public void setColumnsAndDataInTableViewForInvoices(
            TableColumn deleteTableColumn,
            TableView tableView,
            CoreServiceInterface coreServiceInterface) {

        deleteTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        deleteTableColumn.setResizable(false);
        coreServiceInterface.setDataForTableView(tableView);
    }

    public void invokeNewItemWindow(FxmlComponent fxmlComponent) {
        easyFxml.load(fxmlComponent)
                .afterNodeLoaded(pane -> {
                    Scene scene = new Scene(pane, 400, 500);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);

                    stage.show();
                });
    }
}
