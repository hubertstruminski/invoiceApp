package com.hubertstruminski.invoice.app.model;

import java.io.Serializable;

public enum Status implements Serializable {

    SENT("Wysłano"),
    NOT_SENT("Nie wysłano");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
