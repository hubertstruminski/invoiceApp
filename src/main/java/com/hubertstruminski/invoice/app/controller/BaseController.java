package com.hubertstruminski.invoice.app.controller;

import com.hubertstruminski.invoice.app.view.ViewCreator;

public abstract class BaseController {

    private ViewCreator viewCreator;
    private String fxmlName;

    public BaseController() {

    }

    public BaseController(ViewCreator viewCreator, String fxmlName) {
        this.viewCreator = viewCreator;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
