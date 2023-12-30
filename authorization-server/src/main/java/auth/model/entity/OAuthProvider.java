package auth.model.entity;

import jakarta.persistence.*;

@lombok.Setter
@lombok.Getter
@Entity
@Table(name = "oauth_providers")
public class OAuthProvider extends BaseEntity{

    @Column(unique = true)
    private String providerName;

    public static OAuthProvider of(String providerName) {
        OAuthProvider provider = new OAuthProvider();
        provider.setProviderName(providerName);
        return provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OAuthProvider that)) return false;
        if (!super.equals(o)) return false;

        return providerName.equals(that.providerName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + providerName.hashCode();
        return result;
    }
}
