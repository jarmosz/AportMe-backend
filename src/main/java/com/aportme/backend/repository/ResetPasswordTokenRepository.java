package com.aportme.backend.repository;

import com.aportme.backend.entity.ResetPasswordLinkToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordLinkToken, Long> {
    Optional<ResetPasswordLinkToken> findResetPasswordLinkTokenByToken(String token);
}