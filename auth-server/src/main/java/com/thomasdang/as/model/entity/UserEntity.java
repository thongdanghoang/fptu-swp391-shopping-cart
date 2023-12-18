package com.thomasdang.as.model.entity;

import javax.persistence.*;
import java.util.Objects;

@lombok.Getter
@lombok.Setter
@lombok.RequiredArgsConstructor
@lombok.NoArgsConstructor
@Entity
@Table(name = "USER")
public class UserEntity extends BaseEntity implements java.io.Serializable {
    public static final int MAX_FAILED_LOGIN_ATTEMPTS = 5;
    private static final long serialVersionUID = 1L;

    @lombok.NonNull
    @Column(name = "UUID", nullable = false, unique = true)
    private String uuid;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "EMAIL_VERIFIED", nullable = false)
    private boolean emailVerified;

    @Column(name = "NUMBER_OF_FAILED_LOGIN_ATTEMPTS", nullable = false)
    private int maxFailedLoginAttempts;

    @Column(name = "ACCOUNT_NON_LOCKED", nullable = false)
    private boolean accountNonLocked;

    @Column(name = "ACCOUNT_NON_EXPIRED", nullable = false)
    private boolean accountNonExpired;

    @Column(name = "CREDENTIALS_NON_EXPIRED", nullable = false)
    private boolean credentialsNonExpired;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @Column(name = "EXPIRED_DATE")
    private java.time.LocalDateTime expiredDate;

    @Column
    private java.time.LocalDateTime lockedUntil;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "USER_ID")
    private java.util.Set<AuthorityEntity> authorities = new java.util.LinkedHashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private java.util.Set<SocialLinkedEntity> socialLinkedEntities = new java.util.LinkedHashSet<>();

    @Override
    public void onCreate() {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.maxFailedLoginAttempts = 0;
        this.emailVerified = false;
        this.accountNonLocked = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        super.onCreate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        if (!super.equals(o)) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(getUuid(), that.getUuid())
                && Objects.equals(getUsername(), that.getUsername())
                && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUuid(), getUsername(), getEmail());
    }
}
