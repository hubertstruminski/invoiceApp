package com.hubertstruminski.invoice.app.component;

import com.hubertstruminski.invoice.app.controller.TaxWindowController;
import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.api.FxmlFile;
import org.springframework.stereotype.Component;

@Component
public class TaxWindowComponent implements FxmlComponent {

    @Override
    public FxmlFile getFile() {
        return () -> "/static/taxWindow.fxml";
    }

    @Override
    public Class<? extends FxmlController> getControllerClass() {
        return TaxWindowController.class;
    }
}
