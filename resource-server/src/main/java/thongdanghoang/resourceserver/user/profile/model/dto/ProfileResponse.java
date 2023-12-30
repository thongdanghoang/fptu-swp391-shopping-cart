package thongdanghoang.resourceserver.user.profile.model.dto;

import java.time.LocalDateTime;

@lombok.Data
@lombok.Builder
public class ProfileResponse {

    private String userId;
    private String name;
    private String avatarUrl;
    private String email;
    private boolean emailVerified = false;
    private String phone;
    private boolean phoneVerified = false;
    private LocalDateTime birthday;
    private Integer height;
    private Integer weight;
    private String gender;

}
