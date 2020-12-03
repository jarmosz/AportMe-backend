package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.FoundationSurveyDTO;
import com.aportme.backend.exception.UserSurveyWithoutDecisionException;
import com.aportme.backend.facade.FoundationSurveyFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/foundation-survey")
@PreAuthorize("@accessService.isFoundation()")
public class FoundationSurveyController {

    private final FoundationSurveyFacade foundationSurveyFacade;

    @GetMapping
    public FoundationSurveyDTO getMySurvey() {
        return foundationSurveyFacade.getMySurvey();
    }

    @PutMapping("/change-status")
    public ResponseEntity<Object> changeSurveyStatus() {
        foundationSurveyFacade.changeStatus();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UserSurveyWithoutDecisionException.class)
    public ResponseEntity<Object> unresolvedSurvey() {
        return new ResponseEntity<>("You have unresolved surveys", HttpStatus.CONFLICT);
    }
}