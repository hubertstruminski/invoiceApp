package com.hubertstruminski.invoice.app.component;

import com.hubertstruminski.invoice.app.controller.TaxWindowErrorAssignController;
import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.api.FxmlFile;
import org.springframework.stereotype.Component;

@Component
public class TaxWindowErrorAssignComponent implements FxmlComponent {

    @Override
    public FxmlFile getFile() {
        return () -> "/static/taxWindowErrorAssign.fxml";
    }

    @Override
    public Class<? extends FxmlController> getControllerClass() {
        return TaxWindowErrorAssignController.class;
    }
}
