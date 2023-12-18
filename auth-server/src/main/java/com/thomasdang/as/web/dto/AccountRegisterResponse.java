package com.thomasdang.as.web.dto;

import lombok.Data;

@Data
public class AccountRegisterResponse {

    @lombok.NonNull
    String username;

    @lombok.NonNull
    String email;
}
