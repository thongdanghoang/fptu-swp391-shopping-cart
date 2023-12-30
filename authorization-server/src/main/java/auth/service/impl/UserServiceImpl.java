package auth.service.impl;

import auth.model.entity.OAuthProvider;
import auth.model.entity.User;
import auth.model.entity.UserOAuth;
import auth.repository.OAuthProviderRepository;
import auth.repository.UserOAuthRepository;
import auth.repository.UserRepository;
import auth.service.UserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    final UserOAuthRepository userOAuthRepository;

    final OAuthProviderRepository providerRepository;

    final UserRepository userRepository;

    public UserServiceImpl(UserOAuthRepository userOAuthRepository,
                           OAuthProviderRepository providerRepository,
                           UserRepository userRepository) {
        this.userOAuthRepository = userOAuthRepository;
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserIfNotExistsCreateNewOne(OAuth2AuthenticationToken authentication) {

        String providerName = authentication.getAuthorizedClientRegistrationId();
        var userOAuth = userOAuthRepository.findByProviderUserIdAndProviderProviderName(
                getProviderUserIdFromAuthenticationBaseOnProvider(authentication, providerName),
                providerName
        );

        if (userOAuth.isEmpty()) {
            User user = User.of(
                    getAvatarUrlFromAuthenticationBaseOnProvider(authentication, providerName),
                    getNameFromAuthentication(authentication)
            );
            userOAuthRepository.save(UserOAuth.of(
                    userRepository.save(user),
                    providerRepository.findByProviderName(providerName).orElse(
                            providerRepository.save(OAuthProvider.of(providerName))
                    ),
                    getProviderUserIdFromAuthenticationBaseOnProvider(authentication, providerName)));
            return user;
        }

        return userOAuth.get().getUser();
    }

    private String getNameFromAuthentication(OAuth2AuthenticationToken authentication) {
        return Optional.of(authentication)
                .map(OAuth2AuthenticationToken::getPrincipal)
                .map(oAuth2User -> oAuth2User.getAttribute("name"))
                .map(Object::toString)
                .orElse("");
    }

    private String getAvatarUrlFromAuthenticationBaseOnProvider(OAuth2AuthenticationToken authentication, String providerName) {
        return switch (providerName) {
            case "google" -> authentication.getPrincipal().getAttributes().get("picture").toString();
            case "github" -> authentication.getPrincipal().getAttributes().get("avatar_url").toString();
            default -> throw new IllegalArgumentException("Unknown provider: " + providerName);
        };
    }

    private String getProviderUserIdFromAuthenticationBaseOnProvider(OAuth2AuthenticationToken authentication, String providerName) {
        return switch (providerName) {
            case "google" -> authentication.getPrincipal().getAttributes().get("sub").toString();
            case "github" -> authentication.getPrincipal().getAttributes().get("id").toString();
            default -> throw new IllegalArgumentException("Unknown provider: " + providerName);
        };
    }
}
