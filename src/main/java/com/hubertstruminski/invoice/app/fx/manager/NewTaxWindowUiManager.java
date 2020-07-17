package com.hubertstruminski.invoice.app.fx.manager;

import com.hubertstruminski.invoice.app.component.MainWindowComponent;
import com.hubertstruminski.invoice.app.component.NewTaxWindowComponent;
import com.hubertstruminski.invoice.app.controller.NewTaxWindowController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moe.tristan.easyfxml.EasyFxml;
import moe.tristan.easyfxml.FxUiManager;
import moe.tristan.easyfxml.api.FxmlComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "newTaxWindowUiManager")
public class NewTaxWindowUiManager extends FxUiManager {

    private final NewTaxWindowComponent newTaxWindowComponent;

    @Autowired
    public NewTaxWindowUiManager(NewTaxWindowComponent newTaxWindowComponent) {
        this.newTaxWindowComponent = newTaxWindowComponent;
    }

    @Override
    protected String title() {
        return "Add new tax";
    }

    @Override
    protected FxmlComponent mainComponent() {
        return newTaxWindowComponent;
    }
}
