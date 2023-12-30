package thongdanghoang.resourceserver.user.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thongdanghoang.resourceserver.user.profile.model.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByEmail(String email);

    Optional<Profile> findByPhone(String phone);

    Optional<Profile> findByUuid(String uuid);

    boolean existsByEmail(String email);
}
