package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    public MainWindowController() {

    }

    public MainWindowController(ViewCreator viewCreator, String fxmlName) {
        super(viewCreator, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
