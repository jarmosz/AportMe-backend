package com.aportme.backend.repository.survey;

import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.entity.survey.SurveyAnswer;
import com.aportme.backend.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

    void deleteAllByQuestion(SurveyQuestion question);

    void deleteAllByUserSurvey(UserSurvey userSurvey);

    boolean existsByQuestion(SurveyQuestion question);
}
