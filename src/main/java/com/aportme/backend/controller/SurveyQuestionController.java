package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.entity.dto.survey.AddSurveyQuestionsDTO;
import com.aportme.backend.service.survey.SurveyQuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/survey/questions")
@PreAuthorize("@accessService.isFoundation()")
public class SurveyQuestionController {

    private final SurveyQuestionService surveyQuestionService;

    @GetMapping
    @ApiOperation(
            value = "Get all survey question with petId query param to fill survey or without to print logged foundation questions",
            response = SurveyQuestionDTO.class
    )
    public List<SurveyQuestionDTO> getMyQuestions(@RequestParam(required = false) Long petId) {
        return surveyQuestionService.getQuestions(petId);
    }

    @PostMapping("/add")
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Add survey questions")
    public ResponseEntity<Object> createQuestions(@RequestBody AddSurveyQuestionsDTO surveyQuestions) {
        surveyQuestionService.createQuestions(surveyQuestions.getQuestions());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Delete all surveys question for logged foundation")
    public ResponseEntity<Object> deleteAll() {
        surveyQuestionService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isFoundationQuestion(#id)")
    @ApiOperation(value = "Delete survey question by id")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        surveyQuestionService.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
