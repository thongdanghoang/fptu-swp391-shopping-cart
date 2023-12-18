package com.thomasdang.as.web.dto;

@lombok.Value
public class AccountRegisterRequest {
    String username;
    String password;
    String email;
}
