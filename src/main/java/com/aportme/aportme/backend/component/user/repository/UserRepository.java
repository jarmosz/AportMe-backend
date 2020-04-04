package com.aportme.aportme.backend.component.user.repository;

import com.aportme.aportme.backend.component.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}