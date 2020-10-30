package com.aportme.backend.repository.survey;

import com.aportme.backend.entity.survey.SelectValue;
import com.aportme.backend.entity.survey.SurveyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectValueRepository extends JpaRepository<SelectValue, Long> {

    List<SelectValue> findAllByQuestion(SurveyQuestion question);
}
