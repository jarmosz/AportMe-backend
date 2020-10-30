package com.aportme.backend.controller.survey;

import com.aportme.backend.service.survey.SurveyAnswerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/survey/answers")
public class SurveyAnswerController {

    private final SurveyAnswerService surveyAnswerService;
}
