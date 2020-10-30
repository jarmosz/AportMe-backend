package com.aportme.backend.service.survey;

import com.aportme.backend.repository.survey.SurveyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
}
