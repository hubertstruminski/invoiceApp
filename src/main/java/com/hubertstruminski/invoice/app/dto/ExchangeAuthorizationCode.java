package com.hubertstruminski.invoice.app.dto;

public class ExchangeAuthorizationCode {

    private String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String grant_type;

    public void setCode(String code) {
        this.code = code;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
