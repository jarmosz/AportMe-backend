package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.FoundationSurveyDTO;
import com.aportme.backend.facade.FoundationSurveyFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/surveys/foundation")
@PreAuthorize("@accessService.isFoundation()")
public class FoundationSurveyController {

    private final FoundationSurveyFacade foundationSurveyFacade;

    @GetMapping
    public FoundationSurveyDTO getLoggedFoundationSurvey() {
        return foundationSurveyFacade.getLoggedFoundationSurvey();
    }

    @PutMapping("/change-status")
    public ResponseEntity<Object> changeSurveyStatus() {
        foundationSurveyFacade.changeStatus();
        return ResponseEntity.ok().build();
    }
}
