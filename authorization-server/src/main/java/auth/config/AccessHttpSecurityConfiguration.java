package auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AccessHttpSecurityConfiguration {

    @Bean
    @Order(2)
    SecurityFilterChain accessSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http.oauth2Login(Customizer.withDefaults());

        http.authorizeHttpRequests(
                matcherRegistry -> matcherRegistry.anyRequest().authenticated()
        );

        return http.build();
    }

}
