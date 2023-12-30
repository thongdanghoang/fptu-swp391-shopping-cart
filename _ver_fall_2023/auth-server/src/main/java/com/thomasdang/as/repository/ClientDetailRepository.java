package com.thomasdang.as.repository;

import com.thomasdang.as.model.entity.ClientDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientDetailRepository extends JpaRepository<ClientDetailEntity, Long> {
    Optional<ClientDetailEntity> findByClientId(String clientId);
}
