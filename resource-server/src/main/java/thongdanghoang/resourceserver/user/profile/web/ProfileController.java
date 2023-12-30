package thongdanghoang.resourceserver.user.profile.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thongdanghoang.resourceserver.user.profile.model.dto.ProfileResponse;

public interface ProfileController {

    @GetMapping
    ResponseEntity<ProfileResponse> getProfile(BearerTokenAuthentication authentication);

}
