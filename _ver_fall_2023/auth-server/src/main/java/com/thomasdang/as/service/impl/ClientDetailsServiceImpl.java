package com.thomasdang.as.service.impl;

import com.thomasdang.as.model.OAuth2ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final com.thomasdang.as.repository.ClientDetailRepository clientDetailRepository;

    public ClientDetailsServiceImpl(com.thomasdang.as.repository.ClientDetailRepository clientDetailRepository) {
        this.clientDetailRepository = clientDetailRepository;
    }

    @Override
    public OAuth2ClientDetails loadClientByClientId(String clientId)
            throws ClientRegistrationException {

        Supplier<ClientRegistrationException> clientNotFound = () ->
                new ClientRegistrationException(String.format("Client with id %s not found", clientId));

        return clientDetailRepository.findByClientId(clientId)
                .map(OAuth2ClientDetails::new)
                .orElseThrow(clientNotFound);
    }
}
