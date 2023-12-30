package thongdanghoang.resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfiguration {


    @Value("${introspection-uri}")
    private String introspectionUri;
    @Value("${client-id}")
    private String clientId;
    @Value("${client-secret}")
    private String clientSecret;

    @Bean
    @Order(1)
    SecurityFilterChain resourceSecurityFilterChain(HttpSecurity http) throws Exception {

        http.oauth2ResourceServer(
                c -> c.opaqueToken(
                        o -> o.introspectionUri(introspectionUri)
                                .introspectionClientCredentials(clientId, clientSecret)
                )
        ).authorizeHttpRequests(
                c -> c.anyRequest().hasAuthority("SCOPE_n3tk")
        );

        return http.build();
    }

}
