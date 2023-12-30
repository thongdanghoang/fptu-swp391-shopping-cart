package thongdanghoang.resourceserver.user.profile.service;

import thongdanghoang.resourceserver.user.profile.model.dto.ProfileResponse;

public interface ProfileService {
    ProfileResponse getProfile(String userId);
}
