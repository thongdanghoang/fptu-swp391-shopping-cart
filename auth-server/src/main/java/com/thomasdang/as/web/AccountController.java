package com.thomasdang.as.web;

import com.thomasdang.as.model.dto.SuccessResponse;
import com.thomasdang.as.model.exceptions.DataValidationException;
import com.thomasdang.as.service.AccountService;
import com.thomasdang.as.web.dto.AccountRegisterRequest;
import com.thomasdang.as.web.dto.AccountRegisterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController extends BaseController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //register
    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<AccountRegisterResponse>> register(@RequestBody AccountRegisterRequest data) {
        try {
            var result = accountService.register(data);
            SuccessResponse<AccountRegisterResponse> response = new SuccessResponse<>();
            response.setMessage(String.format("Account %s registered successfully", result.getUsername()));
            response.setSuccess(true);
            response.setData(result);
            return ResponseEntity.ok(response);
        } catch (DataValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
