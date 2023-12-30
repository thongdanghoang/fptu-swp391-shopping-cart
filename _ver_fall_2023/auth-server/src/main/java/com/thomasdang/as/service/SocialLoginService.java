package com.thomasdang.as.service;

import com.thomasdang.as.model.entity.AuthorityEntity;
import com.thomasdang.as.model.entity.SocialLinkedEntity;
import com.thomasdang.as.model.entity.UserEntity;
import com.thomasdang.as.model.entity.enums.IdP;
import com.thomasdang.as.repository.AuthorityRepository;
import com.thomasdang.as.repository.SocialLinkedRepository;
import com.thomasdang.as.repository.UserRepository;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SocialLoginService {

    private static final String SUB_ATTRIBUTE = "sub";
    private static final String ID_ATTRIBUTE = "id";
    private static final String EMAIL_ATTRIBUTE = "email";

    private static final String USERNAME = "user_name";
    private static final String AUTHORITIES = AccessTokenConverter.AUTHORITIES;

    private final SocialLinkedRepository socialLinkedRepository;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;


    public SocialLoginService(SocialLinkedRepository socialLinkedRepository,
                              UserRepository userRepository,
                              AuthorityRepository authorityRepository) {
        this.socialLinkedRepository = socialLinkedRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }


    public Map<String, Object> handleLogin(Map<String, Object> response, Map<String, Object> attributes) {
        var id = Optional
                .ofNullable(attributes.get(SUB_ATTRIBUTE)).map(Object::toString)
                .or(() -> Optional.ofNullable(attributes.get(ID_ATTRIBUTE)).map(Object::toString))
                .orElseThrow(() -> new IllegalArgumentException("Cannot find id from attributes"));
        socialLinkedRepository
                .findByProviderUserId(id)
                .ifPresent(socialLinked -> setTokenAdditionalInfo(response, socialLinked));
        return response;
    }

    public void createNewUserIfNotExists(Map<String, Object> attributes) {
        Optional<String> id = Optional.ofNullable(attributes.get("sub")).map(Object::toString);
        IdP provider = IdP.GOOGLE;

        if (id.isEmpty()) {
            id = Optional.ofNullable(attributes.get("id")).map(Object::toString);
            provider = IdP.GITHUB;
        }
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Cannot find id from attributes");
        }

        var found = socialLinkedRepository.findByProviderUserId(id.get());

        if (found.isPresent()) {
            return;
        }

        if (provider == IdP.GOOGLE) {
            registerWithGoogleProfile(attributes);
        }
        if (provider == IdP.GITHUB) {
            registerWithGithubProfile(attributes);
        }
    }

    private void registerWithGoogleProfile(Map<String, Object> attributes) {
        var roleUser = authorityRepository.save(new AuthorityEntity("ROLE_USER"));

        UserEntity user = new UserEntity();
        user.getAuthorities().add(roleUser);
        user.setUsername(attributes.get(EMAIL_ATTRIBUTE).toString());

        UserEntity savedUser = userRepository.save(user);

        SocialLinkedEntity socialLinked = new SocialLinkedEntity();
        socialLinked.setProvider(IdP.GOOGLE);
        socialLinked.setProviderUserId(attributes.get(SUB_ATTRIBUTE).toString());
        socialLinked.setUserEntity(savedUser);
        socialLinkedRepository.save(socialLinked);
    }

    private void registerWithGithubProfile(Map<String, Object> attributes) {
        var roleUser = authorityRepository.save(new AuthorityEntity("ROLE_USER"));

        UserEntity user = new UserEntity();
        user.getAuthorities().add(roleUser);
        user.setUsername(attributes.get("login").toString());

        UserEntity savedUser = userRepository.save(user);

        SocialLinkedEntity socialLinked = new SocialLinkedEntity();
        socialLinked.setProvider(IdP.GITHUB);
        socialLinked.setProviderUserId(attributes.get(ID_ATTRIBUTE).toString());
        socialLinked.setUserEntity(savedUser);
        socialLinkedRepository.save(socialLinked);
    }

    private void setTokenAdditionalInfo(Map<String, Object> response, SocialLinkedEntity socialLinked) {
        response.put(USERNAME, socialLinked.getUserEntity().getUsername());
        List<String> authorities = socialLinked.getUserEntity().getAuthorities()
                .stream()
                .map(AuthorityEntity::getName)
                .collect(Collectors.toList());
        response.put(AUTHORITIES, authorities.isEmpty() ? new ArrayList<>() : authorities);
    }
}
