package com.aportme.backend.component.activationToken.repository;

import com.aportme.backend.component.activationToken.entity.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
    Optional<ActivationToken> findActivationTokenByToken(String token);
}