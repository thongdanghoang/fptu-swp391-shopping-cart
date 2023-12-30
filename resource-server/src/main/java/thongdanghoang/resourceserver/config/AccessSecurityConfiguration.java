package thongdanghoang.resourceserver.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AccessSecurityConfiguration {

    @Bean
    @Order(2)
    SecurityFilterChain accessSecurityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                c -> c
                        .requestMatchers("/actuator/**").permitAll()
        );

        return http.build();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher
            (ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
