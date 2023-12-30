package auth.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
@lombok.Getter
@lombok.Setter
public class User extends BaseEntity{

    @Column
    private String uuid;

    @Column
    private String avatarUrl;

    @Column
    private String name;

    public static User of(String avatarUrl, String name) {
        User user = new User();
        user.setAvatarUrl(avatarUrl);
        user.setName(name);
        return user;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        uuid = java.util.UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(uuid, user.uuid);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }
}
