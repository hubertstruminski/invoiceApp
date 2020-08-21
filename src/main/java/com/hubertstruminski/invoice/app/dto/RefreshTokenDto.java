package com.hubertstruminski.invoice.app.dto;

public class RefreshTokenDto {

    private String client_id;
    private String client_secret;
    private String refresh_token;
    private String grant_type;

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
