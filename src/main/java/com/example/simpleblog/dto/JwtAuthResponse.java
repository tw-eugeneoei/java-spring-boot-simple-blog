package com.example.simpleblog.dto;

import com.example.simpleblog.utils.AppConstants;

public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = AppConstants.TOKEN_TYPE;

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
