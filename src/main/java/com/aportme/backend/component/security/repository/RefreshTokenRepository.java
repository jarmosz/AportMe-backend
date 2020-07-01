package com.aportme.backend.component.security.repository;

import com.aportme.backend.component.security.entity.RefreshToken;
import com.aportme.backend.component.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findRefreshTokenByUser(User user);
}
