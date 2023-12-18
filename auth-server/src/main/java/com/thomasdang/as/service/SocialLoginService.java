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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class SocialLoginService {

    private static final Logger log = Logger.getLogger(SocialLoginService.class.getName());

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
        log.info("handleLogin");
        var id = Optional.ofNullable(attributes.get("sub").toString()); // Google
        if (id.isEmpty()) {
            id = Optional.ofNullable(attributes.get("id").toString()); // Github
        }else{
            log.info("Google logged in");
        }
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Cannot find id from attributes");
        }else{
            log.info("Github logged in");
        }
        socialLinkedRepository.findByProviderUserId(id.get())
                .ifPresentOrElse(socialLinked -> setTokenAdditionalInfo(response, socialLinked),
                        () -> log.info("Cannot find user."));
        return response;
    }

    public void createNewUserIfNotExists(Map<String, Object> attributes) {
        log.info("createNewUserIfNotExists");
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
            log.info("User already exists");
            return;
        }

        if (provider == IdP.GOOGLE) {
            log.info("register with provider == IdP.GOOGLE");
            registerWithGoogleProfile(attributes);
        }
        if(provider == IdP.GITHUB) {
            log.info("register with provider == IdP.GITHUB");
            registerWithGithubProfile(attributes);
        }
    }

    private void registerWithGoogleProfile(Map<String, Object> attributes) {
        log.info("registerWithGoogleProfile");
        var roleUser = authorityRepository.save(new AuthorityEntity("ROLE_USER"));

        UserEntity user = new UserEntity();
        user.getAuthorities().add(roleUser);
        user.setUsername(attributes.get("email").toString());

        UserEntity savedUser = userRepository.save(user);

        SocialLinkedEntity socialLinked = new SocialLinkedEntity();
        socialLinked.setProvider(IdP.GOOGLE);
        socialLinked.setProviderUserId(attributes.get("sub").toString());
        socialLinked.setUserEntity(savedUser);
        socialLinkedRepository.save(socialLinked);
    }

    private void registerWithGithubProfile(Map<String, Object> attributes) {
        log.info("registerWithGithubProfile");
        var roleUser = authorityRepository.save(new AuthorityEntity("ROLE_USER"));

        UserEntity user = new UserEntity();
        user.getAuthorities().add(roleUser);
        user.setUsername(attributes.get("login").toString());

        UserEntity savedUser = userRepository.save(user);

        SocialLinkedEntity socialLinked = new SocialLinkedEntity();
        socialLinked.setProvider(IdP.GITHUB);
        socialLinked.setProviderUserId(attributes.get("id").toString());
        socialLinked.setUserEntity(savedUser);
        socialLinkedRepository.save(socialLinked);
    }

    private void setTokenAdditionalInfo(Map<String, Object> response, SocialLinkedEntity socialLinked) {
        log.info("setTokenAdditionalInfo");
        response.put(USERNAME, socialLinked.getUserEntity().getUsername());
        List<String> authorities = socialLinked.getUserEntity().getAuthorities()
                .stream()
                .map(AuthorityEntity::getName)
                .collect(Collectors.toList());
        response.put(AUTHORITIES, authorities.isEmpty() ? new ArrayList<>() : authorities);
    }


}
