package com.aportme.backend.controller.survey;

import com.aportme.backend.entity.dto.SurveyQuestionDTO;
import com.aportme.backend.entity.dto.survey.AddSurveyQuestionDTODTO;
import com.aportme.backend.service.survey.SurveyQuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("@accessService.isFoundation()")
@RequestMapping("/api/survey/questions")
public class SurveyQuestionController {

    private final SurveyQuestionService surveyQuestionService;

    @GetMapping
    @ApiOperation(value = "Get logged foundation survey questions", response = SurveyQuestionDTO.class)
    public List<SurveyQuestionDTO> getMyQuestions(@RequestParam(required = false) Long petId) {
        return surveyQuestionService.getQuestions(petId);
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add survey questions")
    public ResponseEntity<Object> createQuestions(@RequestBody AddSurveyQuestionDTODTO surveyQuestions) {
        surveyQuestionService.createQuestions(surveyQuestions.getQuestions());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isMyQuestion(#id)")
    @ApiOperation(value = "Delete survey question by id")
    public ResponseEntity<Object> deleteQuestion(@PathVariable Long id) {
        surveyQuestionService.deleteById(id);
        return ResponseEntity.ok().build();

    }
}
