package com.thomasdang.as.config;

import com.thomasdang.as.service.impl.AuthenticationProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProviderService authenticationProvider;

    private final OAuth2SavedRequestAwareAuthenticationSuccessHandler oAuth2SuccessHandler;

    public WebSecurityConfig(AuthenticationProviderService authenticationProvider,
                             OAuth2SavedRequestAwareAuthenticationSuccessHandler oAuth2SuccessHandler) {
        this.authenticationProvider = authenticationProvider;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/v3/api-docs/**").permitAll()
                .mvcMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
                .mvcMatchers("/api/v1/account/**").permitAll()
                .mvcMatchers("/api/v1/oauth2/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().and()
                .oauth2Login().successHandler(oAuth2SuccessHandler);
        //@formatter:on
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Configure allowed origins
        List<String> allowedOrigins = new ArrayList<>();
        allowedOrigins.add("*");
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(false); // Allow sending cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // CORS configuration for other paths
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
