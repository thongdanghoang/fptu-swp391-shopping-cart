package com.thomasdang.as.service;

import com.thomasdang.as.model.entity.AuthorityEntity;
import com.thomasdang.as.model.entity.UserEntity;
import com.thomasdang.as.model.exceptions.DataValidationException;
import com.thomasdang.as.repository.AuthorityRepository;
import com.thomasdang.as.repository.UserRepository;
import com.thomasdang.as.validator.AccountValidator;
import com.thomasdang.as.web.dto.AccountRegisterRequest;
import com.thomasdang.as.web.dto.AccountRegisterResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountValidator validator;

    public AccountService(UserRepository userRepository,
                          AuthorityRepository authorityRepository,
                          PasswordEncoder passwordEncoder,
                          AccountValidator validator) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public AccountRegisterResponse register(AccountRegisterRequest data)
            throws DataValidationException {

        validator.validateRegisterAccount(data);

        var username = data.getUsername();
        var encodedPassword = passwordEncoder.encode(data.getPassword());
        var email = data.getEmail();
        var roleUser = authorityRepository.save(new AuthorityEntity("ROLE_USER"));

        var user = new UserEntity();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.getAuthorities().add(roleUser);

        var saved = userRepository.save(user);
        // expected exception can be thrown here
        // username already exists
        // email already exists
        // data validation

        return new AccountRegisterResponse(saved.getUsername(), saved.getEmail());
    }
}
