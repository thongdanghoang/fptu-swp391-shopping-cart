package com.thomasdang.as.web;

import com.thomasdang.as.model.dto.SuccessResponse;
import com.thomasdang.as.service.OAuth2ClientService;
import com.thomasdang.as.web.dto.ClientRegisterRequest;
import com.thomasdang.as.web.dto.ClientRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/oauth2")
public class OAuth2ClientController extends BaseController {

    private final OAuth2ClientService oAuth2ClientService;

    public OAuth2ClientController(OAuth2ClientService oAuth2ClientService) {
        this.oAuth2ClientService = oAuth2ClientService;
    }

    @PostMapping("/client")
    public ResponseEntity<SuccessResponse<ClientRegisterResponse>> addClient(@RequestBody ClientRegisterRequest data) {
        ClientRegisterResponse result = oAuth2ClientService.addClient(data);

        SuccessResponse<ClientRegisterResponse> response = new SuccessResponse<>();
        response.setMessage(String.format("Client %s registered successfully", result.getClientName()));
        response.setSuccess(true);
        response.setData(result);

        return ResponseEntity.ok(response);
    }
}
