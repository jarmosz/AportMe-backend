package com.aportme.backend.repository;

import com.aportme.backend.entity.ResetPasswordLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordLink, Long> {

    Optional<ResetPasswordLink> findByToken(String token);
}