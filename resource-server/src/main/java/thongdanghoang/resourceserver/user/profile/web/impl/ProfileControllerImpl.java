package thongdanghoang.resourceserver.user.profile.web.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import thongdanghoang.resourceserver.shared.AbstractApplicationController;
import thongdanghoang.resourceserver.template.exceptions.MyResourceNotFoundException;
import thongdanghoang.resourceserver.user.profile.model.dto.ProfileResponse;
import thongdanghoang.resourceserver.user.profile.service.ProfileService;
import thongdanghoang.resourceserver.user.profile.web.ProfileController;

@RestController
@RequestMapping("/api/user/profile")
public class ProfileControllerImpl extends AbstractApplicationController implements ProfileController {

    final ProfileService profileService;

    public ProfileControllerImpl(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public ResponseEntity<ProfileResponse> getProfile(BearerTokenAuthentication authentication) {
        try {
            return ResponseEntity.ok(
                    profileService.getProfile(authentication.getTokenAttributes().get("id").toString())
            );
        } catch (MyResourceNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }
}
