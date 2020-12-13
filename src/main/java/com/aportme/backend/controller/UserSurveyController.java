package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.exception.UnableToDeleteNotSubmittedSurveyException;
import com.aportme.backend.facade.UserSurveyFacade;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user-surveys")
public class UserSurveyController {

    private final UserSurveyFacade userSurveyFacade;

    @GetMapping
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Return all logged user surveys", response = UserSurveyDTO.class)
    public List<UserSurveyDTO> getAllMySurveys() {
        return userSurveyFacade.getAll();
    }

    @PostMapping
    @PreAuthorize("@accessService.isUser()")
    @ApiOperation(value = "Create survey for logged user")
    public ResponseEntity<Object> create(@RequestBody CreateSurveyDTO dto) {
        userSurveyFacade.createSurvey(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@accessService.isSurveyOwner(#id)")
    @ApiOperation(value = "Deletes user survey")
    public ResponseEntity<Object> deleteSurvey(@PathVariable Long id) {
        userSurveyFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(UnableToDeleteNotSubmittedSurveyException.class)
    public ResponseEntity<Object> cannotDeleteSurvey() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
