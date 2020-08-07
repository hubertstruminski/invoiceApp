package com.hubertstruminski.invoice.app.component;

import com.hubertstruminski.invoice.app.controller.ProductWindowController;
import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.api.FxmlFile;
import org.springframework.stereotype.Component;

@Component
public class ProductWindowComponent implements FxmlComponent {

    @Override
    public FxmlFile getFile() {
        return () -> "/static/productWindow.fxml";
    }

    @Override
    public Class<? extends FxmlController> getControllerClass() {
        return ProductWindowController.class;
    }
}
