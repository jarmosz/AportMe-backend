package com.aportme.backend.repository;

import com.aportme.backend.entity.ActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
    Optional<ActivationToken> findActivationTokenByToken(String token);
    Optional<ActivationToken> findByUserId(Long id);
}