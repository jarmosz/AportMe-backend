package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.service.survey.SurveyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping
    public List<UserSurveyDTO> getAllMySurveys() {
        return surveyService.getAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateSurveyDTO dto) {
        surveyService.createSurvey(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSurvey(@PathVariable Long id){
        surveyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
