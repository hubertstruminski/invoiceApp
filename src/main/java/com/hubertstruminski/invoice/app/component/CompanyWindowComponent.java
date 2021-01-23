package com.hubertstruminski.invoice.app.component;

import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.api.FxmlFile;
import org.springframework.stereotype.Component;

@Component
public class CompanyWindowComponent implements FxmlComponent {

    @Override
    public FxmlFile getFile() {
        return () -> "/static/companyWindow.fxml";
    }

    @Override
    public Class<? extends FxmlController> getControllerClass() {
        return null;
    }
}
