package com.aportme.backend.repository.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {

    List<SurveyQuestion> findAllByFoundationAndQuestionStatus(Foundation foundation, QuestionStatus questionStatus);

    void deleteAllByFoundation(Foundation foundation);
}
