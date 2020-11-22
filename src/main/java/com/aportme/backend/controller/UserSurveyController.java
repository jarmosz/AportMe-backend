package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.service.survey.UserSurveyService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/surveys")
public class UserSurveyController {

    private final UserSurveyService userSurveyService;

    @GetMapping
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Return all logged user surveys")
    public List<UserSurveyDTO> getAllMySurveys() {
        return userSurveyService.getAll();
    }

    @PostMapping
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Create survey for logged user")
    public ResponseEntity<Object> create(@RequestBody CreateSurveyDTO dto) {
        userSurveyService.createSurvey(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Deletes user survey")
    public ResponseEntity<Object> deleteSurvey(@PathVariable Long id){
        userSurveyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
