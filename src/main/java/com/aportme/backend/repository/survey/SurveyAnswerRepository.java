package com.aportme.backend.repository.survey;

import com.aportme.backend.entity.survey.SurveyAnswer;
import com.aportme.backend.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

    List<SurveyAnswer> findAllByQuestion(SurveyQuestion question);
}
