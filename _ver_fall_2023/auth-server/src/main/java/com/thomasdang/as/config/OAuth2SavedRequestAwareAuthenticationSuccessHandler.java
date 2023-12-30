package com.thomasdang.as.config;

import com.thomasdang.as.service.SocialLoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SavedRequestAwareAuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    private final SocialLoginService socialLoginService;

    public OAuth2SavedRequestAwareAuthenticationSuccessHandler(SocialLoginService socialLoginService) {
        this.socialLoginService = socialLoginService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication)
            throws ServletException, IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        socialLoginService.createNewUserIfNotExists(token.getPrincipal().getAttributes());
        super.onAuthenticationSuccess(req, res, authentication);
    }
}
