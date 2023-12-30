package com.thomasdang.as.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@lombok.Getter
@lombok.Setter
@lombok.RequiredArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "CLIENT_DETAILS")
public class ClientDetailEntity extends BaseEntity implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @lombok.NonNull
    @Column(name = "CLIENT_NAME", nullable = false)
    private String clientName;

    @lombok.NonNull
    @Column(name = "CLIENT_ID", nullable = false, unique = true)
    private String clientId;

    @lombok.NonNull
    @Column(name = "IS_SECRET_REQUIRED", nullable = false)
    private boolean secretRequired;

    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    @lombok.NonNull
    @Column(name = "IS_SCOPED", nullable = false)
    private boolean scopesRequired;

    @Column(name = "SCOPES")
    private String scopes;

    @Column(name = "ACCESS_TOKEN_VALIDITY_SECONDS")
    private int accessTokenValiditySeconds = 60 * 60;

    @Column(name = "REFRESH_TOKEN_VALIDITY_SECONDS")
    private int refreshTokenValiditySeconds = 60 * (60 * 24 * 30);

    @Column(name = "RESOURCE_IDS")
    private String resourceIds;

    @Column(name = "AUTHORIZED_GRANT_TYPES")
    private String authorizedGrantTypes;

    @Column(name = "REGISTERED_REDIRECT_URI")
    private String registeredRedirectUri;

    @Column(name = "AUTHORITIES")
    private String authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientDetailEntity)) return false;
        if (!super.equals(o)) return false;
        ClientDetailEntity that = (ClientDetailEntity) o;
        return Objects.equals(getClientId(), that.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getClientId());
    }
}
