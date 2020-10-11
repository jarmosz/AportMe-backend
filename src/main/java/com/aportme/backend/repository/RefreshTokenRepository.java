package com.aportme.backend.repository;

import com.aportme.backend.entity.RefreshToken;
import com.aportme.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findTokenByUser(User user);

    void deleteTokenById(Long id);
}
