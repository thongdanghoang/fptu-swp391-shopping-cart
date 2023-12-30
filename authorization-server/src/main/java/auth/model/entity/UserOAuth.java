package auth.model.entity;

import jakarta.persistence.*;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "user_oauth")
public class UserOAuth extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private OAuthProvider provider;

    @Column(unique = true)
    private String providerUserId;

    public static UserOAuth of(User user, OAuthProvider provider, String providerUserId) {
        UserOAuth userOAuth = new UserOAuth();
        userOAuth.setUser(user);
        userOAuth.setProvider(provider);
        userOAuth.setProviderUserId(providerUserId);
        return userOAuth;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
