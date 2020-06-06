package com.hubertstruminski.invoice.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hubertstruminski.invoice.app.config.OAuthAuthenticator;
import com.hubertstruminski.invoice.app.config.OAuthCompletedCallback;
import com.hubertstruminski.invoice.app.config.OAuthGoogleAuthenticator;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class GoogleLoginFormController extends BaseController implements Initializable {

    private WebEngine webEngine;

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
        webEngine = googleLoginWebView.getEngine();
        webEngine.load("http://localhost:5000/");
        OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(
                "192634648100-g8jppcdrarqe437slgrd6np1eci8go7e.apps.googleusercontent.com",
                "https://localhost:5000",
                "ohCMtt1QHecVBCTqQ-bqiYjN",
                "https://www.googleapis.com/auth/userinfo.profile");

        auth.startLogin();

        new OAuthCompletedCallback() {
            @Override
            public void oAuthCallback(OAuthAuthenticator authenticator) {
                String accessToken = authenticator.getAccessToken();
                JSONObject jsonData = authenticator.getJsonData();
                System.out.println("Hubert Strumiński => " + accessToken);
            }
        };
    }

    public void closeGoogleLoginWindow() {
        Stage stage = (Stage) googleLoginWebView.getScene().getWindow();
        stage.close();
    }
}
