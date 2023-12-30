package com.thomasdang.as.repository;

import com.thomasdang.as.model.entity.SocialLinkedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialLinkedRepository extends JpaRepository<SocialLinkedEntity, Long> {
    Optional<SocialLinkedEntity> findByProviderUserId(String providerUserId);
}
