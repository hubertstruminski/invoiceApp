package com.hubertstruminski.invoice.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.hubertstruminski.invoice.app.config.OAuthAuthenticator;
import com.hubertstruminski.invoice.app.config.OAuthCompletedCallback;
import com.hubertstruminski.invoice.app.config.OAuthGoogleAuthenticator;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

public class LoginWindowController extends BaseController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button googleLoginButton;

    public LoginWindowController() {

    }

    public LoginWindowController(ViewCreator viewCreator, String fxmlName) {
        super(viewCreator, fxmlName);
    }

    @FXML
    void googleLoginButtonAction(ActionEvent event) {
        OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(
                "192634648100-d0kdeqe1nn37ijisirvp5rm7hq8ipiq4.apps.googleusercontent.com",
                "oob",
//                "urn:ietf:wg:oauth:2.0:oob:auto",
                "w2cY8VknWPMT_xSm07rlyKRX",
                "https://www.googleapis.com/auth/userinfo.profile");

        auth.startLogin();

        new OAuthCompletedCallback() {
            @Override
            public void oAuthCallback(OAuthAuthenticator authenticator) {
                String accessToken = authenticator.getAccessToken();
                JSONObject jsonData = authenticator.getJsonData();
                System.out.println("Hubert Strumiński => " + accessToken);
            }
        }.oAuthCallback(auth);
        OAuthCompletedCallback oAuthCompletedCallback = new OAuthCompletedCallback() {
            @Override
            public void oAuthCallback(OAuthAuthenticator authenticator) {
                String accessToken = authenticator.getAccessToken();
                JSONObject jsonData = authenticator.getJsonData();
                System.out.println("Hubert Strumiński => " + accessToken);
            }
        };
    }

    @FXML
    void loginButtonAction(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert emailTextField != null : "fx:id=\"emailTextField\" was not injected: check your FXML file 'loginWindow.fxml'.";
        assert passwordTextField != null : "fx:id=\"passwordTextField\" was not injected: check your FXML file 'loginWindow.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'loginWindow.fxml'.";
        assert googleLoginButton != null : "fx:id=\"googleLoginButton\" was not injected: check your FXML file 'loginWindow.fxml'.";

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
