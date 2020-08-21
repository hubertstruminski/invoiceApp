package com.hubertstruminski.invoice.app.dto;

public class GoogleTokenResponse {

    private String access_token;
    private long expires_in;
    private String id_token;
    private String refresh_token;
    private String scope;
    private String token_type;

    public String getAccess_token() {
        return access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}
