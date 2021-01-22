package com.aportme.backend.repository;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.survey.FoundationSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationSurveyRepository extends JpaRepository<FoundationSurvey, Long> {

    Optional<FoundationSurvey> findByFoundation(Foundation foundation);
}
