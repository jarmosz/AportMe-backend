package com.aportme.backend.component.security.repository;

import com.aportme.backend.component.security.entity.RefreshToken;
import com.aportme.backend.component.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findTokenByUser(User user);
    Optional<RefreshToken> deleteTokenById(Long id);
}
