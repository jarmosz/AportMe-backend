package com.aportme.backend.component.foundation.repository;

import com.aportme.backend.component.foundation.entity.FoundationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationInfoRepository extends JpaRepository<FoundationInfo, Long> {

    @Query(value = "SELECT * FROM FOUNDATION_INFO LEFT JOIN PET WHERE PET.FOUNDATION_INFO_ID=FOUNDATION_INFO.ID AND PET.ID=?1", nativeQuery = true)
    Optional<FoundationInfo> findByPetId(Long id);
}
