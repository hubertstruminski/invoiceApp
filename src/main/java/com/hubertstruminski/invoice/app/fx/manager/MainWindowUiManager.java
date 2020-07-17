package com.hubertstruminski.invoice.app.fx.manager;

import com.hubertstruminski.invoice.app.component.MainWindowComponent;
import com.hubertstruminski.invoice.app.controller.MainWindowController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moe.tristan.easyfxml.FxUiManager;
import moe.tristan.easyfxml.api.FxmlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component(value = "mainWindowUiManager")
@Primary
public class MainWindowUiManager extends FxUiManager {

    private final MainWindowComponent mainWindowComponent;

    @Autowired
    public MainWindowUiManager(MainWindowComponent mainWindowComponent) {
        this.mainWindowComponent = mainWindowComponent;
    }

    @Override
    protected String title() {
        return "Invoice App";
    }

    @Override
    protected FxmlComponent mainComponent() {
        return mainWindowComponent;
    }
}
