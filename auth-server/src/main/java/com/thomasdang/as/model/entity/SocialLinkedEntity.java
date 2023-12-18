package com.thomasdang.as.model.entity;

import com.thomasdang.as.model.entity.enums.IdP;

import javax.persistence.*;
import java.util.Objects;

@lombok.Getter
@lombok.Setter
@lombok.RequiredArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "SOCIAL_LINKED")
public class SocialLinkedEntity extends BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @lombok.NonNull
    @Column(name = "PROVIDER", nullable = false)
    @Enumerated(EnumType.STRING)
    private IdP provider;

    @lombok.NonNull
    @Column(name = "PROVIDER_USER_ID", nullable = false, unique = true)
    private String providerUserId;

    @lombok.NonNull
    @ManyToOne
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SocialLinkedEntity)) return false;
        if (!super.equals(o)) return false;
        SocialLinkedEntity that = (SocialLinkedEntity) o;
        return Objects.equals(getProviderUserId(), that.getProviderUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProviderUserId());
    }
}
