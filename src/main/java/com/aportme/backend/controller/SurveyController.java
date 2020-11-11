package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.service.survey.SurveyService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Return all logged user surveys")
    public List<UserSurveyDTO> getAllMySurveys() {
        return surveyService.getAll();
    }

    @PostMapping
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Create survey for logged user")
    public ResponseEntity<Object> create(@RequestBody CreateSurveyDTO dto) {
        surveyService.createSurvey(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Deletes user survey")
    public ResponseEntity<Object> deleteSurvey(@PathVariable Long id){
        surveyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
