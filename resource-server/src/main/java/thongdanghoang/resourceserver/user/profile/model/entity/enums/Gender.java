package thongdanghoang.resourceserver.user.profile.model.entity.enums;

@lombok.Getter
public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

}
