package com.hubertstruminski.invoice.app.component;

import com.hubertstruminski.invoice.app.controller.NewInvoiceWindowController;
import moe.tristan.easyfxml.api.FxmlComponent;
import moe.tristan.easyfxml.api.FxmlController;
import moe.tristan.easyfxml.api.FxmlFile;
import org.springframework.stereotype.Controller;

@Controller
public class NewInvoiceWindowComponent implements FxmlComponent {

    @Override
    public FxmlFile getFile() {
        return () -> "/static/newInvoiceWindow.fxml";
    }

    @Override
    public Class<? extends FxmlController> getControllerClass() {
        return NewInvoiceWindowController.class;
    }
}
