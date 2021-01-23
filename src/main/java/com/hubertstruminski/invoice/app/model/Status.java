package com.hubertstruminski.invoice.app.model;

public enum Status {

    SENT("Wysłano"),
    NOT_SENT("Nie wysłano");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
