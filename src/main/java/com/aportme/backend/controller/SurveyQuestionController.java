package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.CreateSurveyQuestionDTO;
import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.exception.UserSurveyAlreadyExistsException;
import com.aportme.backend.facade.SurveyQuestionFacade;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/survey-questions")
public class SurveyQuestionController {

    private final SurveyQuestionFacade surveyQuestionFacade;

    @GetMapping
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(
            value = "Get all survey question for pet",
            response = SurveyQuestionDTO.class
    )
    public List<SurveyQuestionDTO> getQuestions(@RequestParam Long petId) {
        return surveyQuestionFacade.getQuestions(petId);
    }

    @PostMapping("/add")
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Add survey questions")
    public ResponseEntity<Object> createQuestions(@RequestBody CreateSurveyQuestionDTO question) {
        SurveyQuestionDTO response = surveyQuestionFacade.createQuestion(question);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    @PreAuthorize("@accessService.isFoundation()")
    @ApiOperation(value = "Delete all surveys question for logged foundation")
    public ResponseEntity<Object> deleteAll() {
        surveyQuestionFacade.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isFoundationQuestion(#id)")
    @ApiOperation(value = "Delete survey question by id")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        surveyQuestionFacade.deleteById(id);
        return ResponseEntity.ok().build();

    }

    @ExceptionHandler(UserSurveyAlreadyExistsException.class)
    public ResponseEntity<Object> userSurveyAlreadyExists() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
