package com.hubertstruminski.invoice.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class GoogleLoginFormController extends BaseController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private WebView googleLoginWebView;

    public GoogleLoginFormController() {

    }

    public GoogleLoginFormController(ViewCreator viewCreator, String fxmlName) {
        super(viewCreator, fxmlName);
    }

    @FXML
    void initialize() {
        assert googleLoginWebView != null : "fx:id=\"googleLoginWebView\" was not injected: check your FXML file 'googleLoginFormWindow.fxml'.";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        WebEngine engine = googleLoginWebView.getEngine();

//        JSObject window = (JSObject) engine.executeScript("window");
//        window.setMember("googleLoginController", new GoogleLoginFormController());

        engine.load("http://localhost:5000/");
    }

    public void closeGoogleLoginWindow() {
        Stage stage = (Stage) googleLoginWebView.getScene().getWindow();
        stage.close();
    }
}
