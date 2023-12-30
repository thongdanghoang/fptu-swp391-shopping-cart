package com.thomasdang.as.validator;

import com.thomasdang.as.model.exceptions.DataValidationException;
import com.thomasdang.as.web.dto.AccountRegisterRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AccountValidator {

    //    Minimum eight characters, at least one letter and one number:
    private static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String REGEX_EMAIL_RFC5322 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public void validateRegisterAccount(AccountRegisterRequest register)
            throws DataValidationException {
        validateEmail(register.getEmail());
        validatePassword(register.getPassword());
    }

    public void validateEmail(String email)
            throws DataValidationException {
        if (StringUtils.isEmpty(email)) {
            throw new DataValidationException(String.format("Email %s must not be empty", email));
        }
        if (!email.matches(REGEX_EMAIL_RFC5322))
            throw new DataValidationException(String.format("Email %s is invalid", email));
    }

    public void validatePassword(String password)
            throws DataValidationException {
        if (StringUtils.isEmpty(password)) {
            throw new DataValidationException(String.format("Password %s must not be empty", password));
        }
        if (!password.matches(REGEX_PASSWORD))
            throw new DataValidationException(String.format("Password %s is invalid", password));
    }
}
