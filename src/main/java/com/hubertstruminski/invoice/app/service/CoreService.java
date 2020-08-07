package com.hubertstruminski.invoice.app.service;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Service;

@Service
public class CoreService {

    public void setColumnsAndDataInTableView(
            TableColumn deleteTableColumn,
            TableColumn nameTableColumn,
            TableView tableView,
            CoreServiceInterface coreServiceInterface) {

        deleteTableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        deleteTableColumn.setResizable(false);

        coreServiceInterface.setDataForTableView(tableView);
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}