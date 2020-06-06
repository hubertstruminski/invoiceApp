package com.hubertstruminski.invoice.app.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.hubertstruminski.invoice.app.config.OAuthAuthenticator;
import com.hubertstruminski.invoice.app.config.OAuthCompletedCallback;
import com.hubertstruminski.invoice.app.config.OAuthGoogleAuthenticator;
import com.hubertstruminski.invoice.app.view.ViewCreator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
//        googleLoginButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                try {
//                    new ProcessBuilder("x-www-browser", "http://localhost:5000/").start();
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
//            }
//        });
//        ViewCreator viewCreator = new ViewCreator();
//        viewCreator.showGoogleLoginFormWindow();

    }

    @FXML
    void loginButtonAction(ActionEvent event) {

        OAuthGoogleAuthenticator auth = new OAuthGoogleAuthenticator(
                "192634648100-g8jppcdrarqe437slgrd6np1eci8go7e.apps.googleusercontent.com",
                "https://localhost:5000",
                "ohCMtt1QHecVBCTqQ-bqiYjN",
                "https://www.googleapis.com/auth/userinfo.profile");

        auth.startLogin();

//        new OAuthCompletedCallback() {
//            @Override
//            public void oAuthCallback(OAuthAuthenticator authenticator) {
//                String accessToken = authenticator.getAccessToken();
//                JSONObject jsonData = authenticator.getJsonData();
//                System.out.println("Hubert Strumiński => " + accessToken);
//            }
//        };
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
