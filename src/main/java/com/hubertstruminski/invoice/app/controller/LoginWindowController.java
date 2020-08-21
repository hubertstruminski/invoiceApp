package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.component.EmailWindowComponent;
import com.hubertstruminski.invoice.app.component.PdfWindowComponent;
import com.hubertstruminski.invoice.app.dto.*;
import com.hubertstruminski.invoice.app.model.Token;
import com.hubertstruminski.invoice.app.repository.TokenRepository;
import com.hubertstruminski.invoice.app.service.MainWindowService;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginWindowController implements FxmlController {

    private static final String GOOGLE_API_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob:auto";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String REFRESH_GRANT_TYPE = "refresh_token";
    private static final String ENDPOINT = "https://oauth2.googleapis.com/token";

    private static String CLIENT_ID = "";
    private static String CLIENT_SECRET = "";

    private final EasyFxml easyFxml;
    private final TokenRepository tokenRepository;
    private final PdfWindowComponent pdfWindowComponent;
    private final MainWindowService mainWindowService;
    private final EmailWindowComponent emailWindowComponent;

    private Stage stage = null;

    @Autowired
    public LoginWindowController(
            EasyFxml easyFxml,
            TokenRepository tokenRepository,
            PdfWindowComponent pdfWindowComponent,
            MainWindowService mainWindowService,
            EmailWindowComponent emailWindowComponent) {
        this.easyFxml = easyFxml;
        this.tokenRepository = tokenRepository;
        this.pdfWindowComponent = pdfWindowComponent;
        this.mainWindowService = mainWindowService;
        this.emailWindowComponent = emailWindowComponent;
    }

    @FXML
    private VBox vBox;

    public void initGoogleCredentials() {
        String line = null;
        try {
            line = new String(this.getClass().getResourceAsStream("/google-login.txt").readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String[] splitData = line.split("X123X");
        CLIENT_ID = splitData[0];
        CLIENT_SECRET = splitData[1];
    }

    public String getWebUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth?scope=" + GOOGLE_API_SCOPE
                + "&access_type=offline&redirect_uri=" + REDIRECT_URI + "&response_type=code&client_id="
                + CLIENT_ID;
    }

    public void logIn() {
        WebView root = new WebView();
        WebEngine engine = initializeStageAndWebView(root);
        engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                String result = java.net.URLDecoder.decode(engine.getLocation(), StandardCharsets.UTF_8);

                if(result.startsWith("https://accounts.google.com/o/oauth2/approval/v2")) {
                    String accessCode = result.substring(result.indexOf("code=") + 5);
                    saveToken(accessCode);

                    if(stage != null) {
                        stage.close();
                    }
                }
            }
        });
        showWindow(root);
    }

    public WebEngine initializeStageAndWebView(WebView root) {
        WebEngine engine = root.getEngine();

        String webUrl = getWebUrl();
        engine.load(webUrl);

        return engine;
    }

    private void saveToken(String accessCode) {
        GoogleTokenResponse googleTokenResponse = exchangeAuthorizationCodeForAccessAndRefreshTokens(accessCode);
        Token token = new Token();

        token.setAccessToken(googleTokenResponse.getAccess_token());
        token.setRefreshToken(googleTokenResponse.getRefresh_token());
        token.setExpiresIn(googleTokenResponse.getExpires_in());

        tokenRepository.save(token);

        mainWindowService.onLoadComponent(
                emailWindowComponent,
                700,
                600,
                true,
                "Wyślij e-mail");
    }

    private void showWindow(WebView root) {
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private GoogleTokenResponse exchangeAuthorizationCodeForAccessAndRefreshTokens(String code) {
        RestTemplate restTemplate = new RestTemplate();
        ExchangeAuthorizationCode authorizationCodeObjectExchange = new ExchangeAuthorizationCode();

        authorizationCodeObjectExchange.setCode(code);
        authorizationCodeObjectExchange.setClient_id(CLIENT_ID);
        authorizationCodeObjectExchange.setClient_secret(CLIENT_SECRET);
        authorizationCodeObjectExchange.setRedirect_uri(REDIRECT_URI);
        authorizationCodeObjectExchange.setGrant_type(GRANT_TYPE);

        HttpEntity<ExchangeAuthorizationCode> request = new HttpEntity<>(authorizationCodeObjectExchange);
        return restTemplate.postForObject(ENDPOINT, request, GoogleTokenResponse.class);
    }

    private RefreshTokenResponse refreshAccessToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        RefreshTokenDto refreshTokenDto = new RefreshTokenDto();

        refreshTokenDto.setClient_id(CLIENT_ID);
        refreshTokenDto.setClient_secret(CLIENT_SECRET);
        refreshTokenDto.setRefresh_token(refreshToken);
        refreshTokenDto.setGrant_type(REFRESH_GRANT_TYPE);

        HttpEntity<RefreshTokenDto> request = new HttpEntity<>(refreshTokenDto);
        return restTemplate.postForObject(ENDPOINT, request, RefreshTokenResponse.class);
    }

    private void saveAfterRefreshToken(Token token, RefreshTokenResponse response) {
        token.setAccessToken(response.getAccess_token());
        token.setExpiresIn(response.getExpires_in());

        tokenRepository.save(token);

        mainWindowService.onLoadComponent(
                emailWindowComponent,
                700,
                600,
                true,
                "Wyślij e-mail");
    }


    @Override
    public void initialize() {
        initGoogleCredentials();

        Iterable<Token> all = tokenRepository.findAll();
        List<Token> tokens = new ArrayList<>();
        all.forEach(tokens::add);

        if(tokens.size() > 0) {
            Token token = tokens.get(0);

            RefreshTokenResponse response = refreshAccessToken(token.getRefreshToken());
            saveAfterRefreshToken(token, response);
        } else {
            logIn();
        }
    }
}
