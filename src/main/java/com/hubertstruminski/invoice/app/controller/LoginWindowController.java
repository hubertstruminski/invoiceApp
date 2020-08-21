package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.dto.*;
import com.hubertstruminski.invoice.app.model.Token;
import com.hubertstruminski.invoice.app.repository.TokenRepository;
import com.hubertstruminski.invoice.app.service.LoginWindowService;

import javafx.stage.Stage;
import moe.tristan.easyfxml.api.FxmlController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginWindowController implements FxmlController {

    private final TokenRepository tokenRepository;
    private final LoginWindowService loginWindowService;

    private Stage stage = null;

    @Autowired
    public LoginWindowController(
            TokenRepository tokenRepository,
            LoginWindowService loginWindowService) {
        this.tokenRepository = tokenRepository;
        this.loginWindowService = loginWindowService;
    }

    @Override
    public void initialize() {
        loginWindowService.initGoogleCredentials();

        Iterable<Token> all = tokenRepository.findAll();
        List<Token> tokens = new ArrayList<>();
        all.forEach(tokens::add);

        if(tokens.size() > 0) {
            Token token = tokens.get(0);

            RefreshTokenResponse response = loginWindowService.refreshAccessToken(token.getRefreshToken());
            loginWindowService.saveAfterRefreshToken(token, response);
        } else {
            loginWindowService.logIn(stage);
        }
    }
}
