package com.thomasdang.as.web.dto;

@lombok.Value
public class ClientRegisterRequest {
    String clientName;
    String redirectUris;
    String grantTypes;
    String scopes;
    Integer accessTokenValidity;
    Integer refreshTokenValidity;
}
