package auth.repository;

import auth.model.entity.UserOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOAuthRepository extends JpaRepository<UserOAuth, Integer> {

    Optional<UserOAuth> findByProviderUserIdAndProviderProviderName(String providerUserId, String providerName);

}
