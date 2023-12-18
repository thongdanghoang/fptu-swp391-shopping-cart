package com.thomasdang.as.service;

import com.thomasdang.as.model.entity.ClientDetailEntity;
import com.thomasdang.as.repository.ClientDetailRepository;
import com.thomasdang.as.util.RandomPasswordGenerator;
import com.thomasdang.as.web.dto.ClientRegisterRequest;
import com.thomasdang.as.web.dto.ClientRegisterResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class OAuth2ClientService {

    private final ClientDetailRepository clientDetailRepository;

    private final PasswordEncoder passwordEncoder;

    public OAuth2ClientService(ClientDetailRepository clientDetailRepository,
                               PasswordEncoder passwordEncoder) {
        this.clientDetailRepository = clientDetailRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ClientRegisterResponse addClient(ClientRegisterRequest clientDetails) {
        var clientId = UUID.randomUUID().toString();

        var isSecretRequired = true;
        var clientSecret = RandomPasswordGenerator.generateRandomPassword(32);

        var scopes = clientDetails.getScopes();
        var isScopesRequired = true;

        var name = clientDetails.getClientName();
        var grantTypes = clientDetails.getGrantTypes();
        var redirectUris = clientDetails.getRedirectUris();
        Integer accessTokenValidity = clientDetails.getAccessTokenValidity();
        Integer refreshTokenValidity = clientDetails.getRefreshTokenValidity();

        var clientDetailEntity = new ClientDetailEntity(name, clientId, isSecretRequired, isScopesRequired);
        clientDetailEntity.setClientSecret(passwordEncoder.encode(clientSecret));
        clientDetailEntity.setScopes(scopes);
        clientDetailEntity.setAuthorizedGrantTypes(grantTypes);
        clientDetailEntity.setRegisteredRedirectUri(redirectUris);
        if (accessTokenValidity != null) {
            clientDetailEntity.setAccessTokenValiditySeconds(accessTokenValidity);
        }
        if (refreshTokenValidity != null) {
            clientDetailEntity.setRefreshTokenValiditySeconds(refreshTokenValidity);
        }

        var saved = clientDetailRepository.save(clientDetailEntity);

        return ClientRegisterResponse.builder()
                .clientName(saved.getClientName())
                .clientId(saved.getClientId())
                .clientSecret(clientSecret)
                .redirectUris(saved.getRegisteredRedirectUri())
                .grantTypes(saved.getAuthorizedGrantTypes())
                .scopes(saved.getScopes())
                .accessTokenValidity(saved.getAccessTokenValiditySeconds())
                .refreshTokenValidity(saved.getRefreshTokenValiditySeconds())
                .build();
    }

}
