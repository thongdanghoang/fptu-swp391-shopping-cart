package com.thomasdang.as.model.entity.enums;

@lombok.Getter
public enum IdP {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    GITHUB("github");

    private final String name;

    IdP(String name) {
        this.name = name;
    }
}
