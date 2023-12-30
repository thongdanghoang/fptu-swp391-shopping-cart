package auth.service;

import auth.model.entity.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface UserService {

    User loadUserIfNotExistsCreateNewOne(OAuth2AuthenticationToken authentication);
}
