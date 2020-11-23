package com.aportme.backend.repository.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.enums.SurveyStatus;
import com.aportme.backend.entity.survey.UserSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSurveyRepository extends JpaRepository<UserSurvey, Long> {

    List<UserSurvey> findAllByUser(User user);

    boolean existsByFoundationAndStatus(Foundation foundation, SurveyStatus survey);
}
