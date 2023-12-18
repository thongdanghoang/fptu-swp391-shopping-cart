package com.thomasdang.as.config;

import com.thomasdang.as.service.SocialLoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    private final SocialLoginService socialLoginService;

    private final Logger logger = Logger.getLogger(CustomUserAuthenticationConverter.class.getName());

    public CustomUserAuthenticationConverter(SocialLoginService socialLoginService) {
        this.socialLoginService = socialLoginService;
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            try {
                logger.info("OAuth2AuthenticationToken");
                var token = (OAuth2AuthenticationToken) authentication;
                Map<String, Object> attributes = token.getPrincipal().getAttributes();
                return socialLoginService.handleLogin(new LinkedHashMap<>(), attributes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("DefaultUserAuthenticationConverter");
        return super.convertUserAuthentication(authentication);
    }
}
