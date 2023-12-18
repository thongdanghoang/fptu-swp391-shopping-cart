package com.thomasdang.as.model;

import com.thomasdang.as.model.entity.ClientDetailEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;


@lombok.Getter
public class OAuth2ClientDetails implements ClientDetails {

    private final ClientDetailEntity clientDetailEntity;

    public OAuth2ClientDetails(ClientDetailEntity clientDetailEntity) {
        this.clientDetailEntity = clientDetailEntity;
    }

    /**
     * Additional information for this client, not needed by the vanilla OAuth protocol but might be useful, for example,
     * for storing descriptive information.
     *
     * @return a map of additional information
     */
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new LinkedHashMap<>();
    }

    /**
     * The client id.
     *
     * @return The client id.
     */
    @Override
    public String getClientId() {
        return clientDetailEntity.getClientId();
    }

    @Override
    public boolean isSecretRequired() {
        return clientDetailEntity.isSecretRequired();
    }

    @Override
    public String getClientSecret() {
        return clientDetailEntity.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return clientDetailEntity.isScopesRequired();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clientDetailEntity.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clientDetailEntity.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (!StringUtils.isEmpty(clientDetailEntity.getAuthorities())) {
            Arrays.stream(trimStringElementFrom(clientDetailEntity.getAuthorities().split(",")))
                    .map(SimpleGrantedAuthority::new)
                    .forEach(grantedAuthorities::add);
        }
        return grantedAuthorities;
    }

    /**
     * The resources that this client can access. Can be ignored by callers if empty.
     *
     * @return The resources of this client.
     */
    @Override
    public Set<String> getResourceIds() {
        return mapStringToSet(clientDetailEntity.getResourceIds());
    }

    @Override
    public Set<String> getScope() {
        return mapStringToSet(clientDetailEntity.getScopes());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return mapStringToSet(clientDetailEntity.getAuthorizedGrantTypes());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return mapStringToSet(clientDetailEntity.getRegisteredRedirectUri());
    }

    private LinkedHashSet<String> mapStringToSet(String clientDetail) {
        return StringUtils.isEmpty(clientDetail) ?
                new LinkedHashSet<>() :
                new LinkedHashSet<>(Arrays.asList(trimStringElementFrom(clientDetail.split(","))));
    }

    private String[] trimStringElementFrom(String[] resourceIds) {
        return Arrays.stream(resourceIds)
                .map(String::trim)
                .toArray(String[]::new);
    }
}
