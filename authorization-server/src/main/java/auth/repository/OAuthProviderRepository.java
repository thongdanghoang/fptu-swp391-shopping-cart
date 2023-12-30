package auth.repository;

import auth.model.entity.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthProviderRepository extends JpaRepository<OAuthProvider, Integer>{
    Optional<OAuthProvider> findByProviderName(String providerName);
}
