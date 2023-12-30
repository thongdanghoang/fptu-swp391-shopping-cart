package com.thomasdang.as.service.impl;

import com.thomasdang.as.model.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private final JpaUserDetailsService jpaUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    //@formatter:off
    public AuthenticationProviderService(JpaUserDetailsService jpaUserDetailsService,
                                         PasswordEncoder passwordEncoder) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    //@formatter:on

    @Override
    @Transactional(noRollbackFor = BadCredentialsException.class)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();

        var userDetails = jpaUserDetailsService.loadUserByUsername(username);
        var user = userDetails.getUser();

        // user is disabled contact admin
        if (!user.isEnabled()) {
            throw new BadCredentialsException(String.format("User %s is disabled", username));
        }

        // reach max failed login attempts -> lock account
        if (user.isAccountNonLocked() && (user.getMaxFailedLoginAttempts() >= UserEntity.MAX_FAILED_LOGIN_ATTEMPTS)) {
            user.setAccountNonLocked(false);
            user.setLockedUntil(LocalDateTime.now().plusMinutes(10));
        }

        // account is locked -> check if lock time is over
        if (!user.isAccountNonLocked()) {
            if (user.getLockedUntil().isAfter(LocalDateTime.now())) { // time not over
                throw new BadCredentialsException(
                        String.format("User %s is locked until %s", username, user.getLockedUntil()));
            } else { // time out
                user.setAccountNonLocked(true);
                user.setMaxFailedLoginAttempts(0);
                user.setLockedUntil(null);
            }
        }

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            user.setMaxFailedLoginAttempts(0);
            return new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities());
        } else { // wrong password -> increase failed login attempts
            user.setMaxFailedLoginAttempts(user.getMaxFailedLoginAttempts() + 1);
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
