package com.aportme.aportme.backend.repository;

import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationInfoRepository extends JpaRepository<FoundationInfo, Long> {

    @Query("SELECT f FROM FoundationInfo f LEFT JOIN Pet p where f.id= ?1")
    Optional<FoundationInfo> getFoundationInfoByPetId(Long id);
}
