package thongdanghoang.resourceserver.user.profile.model.entity;

import jakarta.persistence.*;
import thongdanghoang.resourceserver.user.profile.model.entity.enums.Gender;
import thongdanghoang.resourceserver.shared.BaseEntity;

import java.time.LocalDateTime;

@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "profile")
public class Profile extends BaseEntity {

    @Column(unique = true)
    private String uuid;

    @Column
    private String name;

    @Column
    private String avatarUrl;

    @Column(unique = true)
    private String email;

    @Column
    private boolean emailVerified = false;

    @Column(unique = true)
    private String phone;

    @Column
    private boolean phoneVerified = false;

    @Column
    private LocalDateTime birthday;

    @Column
    private Integer height;

    @Column
    private Integer weight;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public static Profile withDefault(String uuid, String name, String avatarUrl) {
        Profile profile = new Profile();
        profile.setUuid(uuid);
        profile.setName(name);
        profile.setAvatarUrl(avatarUrl);
        return profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile profile)) return false;
        if (!super.equals(o)) return false;

        return uuid.equals(profile.uuid);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + uuid.hashCode();
        return result;
    }
}
