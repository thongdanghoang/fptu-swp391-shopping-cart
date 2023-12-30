package com.thomasdang.as.web.dto;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.Builder
public class ClientRegisterResponse {
    private String clientName;
    private String clientId;
    private String clientSecret;
    private String redirectUris;
    private String grantTypes;
    private String scopes;
    private Integer accessTokenValidity;
    private Integer refreshTokenValidity;
}
