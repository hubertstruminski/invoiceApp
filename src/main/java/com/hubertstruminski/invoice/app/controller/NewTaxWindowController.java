package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NewTaxWindowController extends BaseController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label taxAmountLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField taxAmountTextField;

    public NewTaxWindowController() {

    }

    public NewTaxWindowController(ViewCreator viewCreator, String fxmlName) {
        super(viewCreator, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
