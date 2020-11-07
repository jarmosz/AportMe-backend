package com.aportme.backend.repository.survey;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findAllByUser(User user);
}
