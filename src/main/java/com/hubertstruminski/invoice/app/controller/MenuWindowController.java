package com.hubertstruminski.invoice.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

@Controller
public class MenuWindowController extends BaseController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button configurationButton;

    @FXML
    private Label configurationMessageWarningLabel;

    public MenuWindowController() {  }

    public MenuWindowController(ViewCreator viewCreator, String fxmlName) {
        super(viewCreator, fxmlName);
    }

    @FXML
    void configurationButtonAction(ActionEvent event) {

    }

    @FXML
    void createAccountButtonAction(ActionEvent event) {

    }

    @FXML
    void loginButtonAction(ActionEvent event) {
        ViewCreator viewCreator = new ViewCreator();
        viewCreator.showLoginWindow();
    }

    @FXML
    void initialize() {
        assert createAccountButton != null : "fx:id=\"createAccountButton\" was not injected: check your FXML file 'menuWindow.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'menuWindow.fxml'.";
        assert configurationButton != null : "fx:id=\"configurationButton\" was not injected: check your FXML file 'menuWindow.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurationMessageWarningLabel.setText("");
    }
}
