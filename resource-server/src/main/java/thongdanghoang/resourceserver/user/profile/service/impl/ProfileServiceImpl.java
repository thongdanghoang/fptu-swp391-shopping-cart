package thongdanghoang.resourceserver.user.profile.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thongdanghoang.resourceserver.template.exceptions.MyResourceNotFoundException;
import thongdanghoang.resourceserver.user.profile.model.dto.ProfileResponse;
import thongdanghoang.resourceserver.user.profile.model.entity.Profile;
import thongdanghoang.resourceserver.user.profile.repository.ProfileRepository;
import thongdanghoang.resourceserver.user.profile.service.ProfileService;

import java.util.Optional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ProfileResponse getProfile(String userId) {
        Optional<Profile> found = profileRepository
                .findByUuid(userId);

        if (found.isEmpty()) {
            throw new MyResourceNotFoundException(String.format("Profile with id %s not found", userId));
        }

        Optional<ProfileResponse> profileResponse = found.map(
                profile -> ProfileResponse.builder()
                        .userId(profile.getUuid())
                        .avatarUrl(profile.getAvatarUrl())
                        .email(profile.getEmail())
                        .emailVerified(profile.isEmailVerified())
                        .phone(profile.getPhone())
                        .phoneVerified(profile.isPhoneVerified())
                        .name(profile.getName())
                        .gender(profile.getGender().getDisplayName())
                        .birthday(profile.getBirthday())
                        .height(profile.getHeight())
                        .weight(profile.getWeight())
                        .build()
        );

        if (profileResponse.isEmpty())
            throw new RuntimeException(String.format("Profile with id %s processed unsuccessfully", userId));

        return profileResponse.get();
    }
}
