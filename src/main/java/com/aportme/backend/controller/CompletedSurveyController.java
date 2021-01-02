package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.survey.UpdateSurveyStatusDTO;
import com.aportme.backend.entity.dto.survey.CompletedSurveyDTO;
import com.aportme.backend.service.survey.CompletedSurveyService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("@accessService.isFoundation()")
@RequestMapping("/api/surveys/completed")
public class CompletedSurveyController {

    private final CompletedSurveyService completedSurveyService;

    @GetMapping
    @ApiOperation(value = "Return all user surveys for foundation", response = CompletedSurveyDTO.class)
    public Page<CompletedSurveyDTO> getCompletedSurveys(
            @RequestParam(required = false) String search,
            @SortDefault(value = "status", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return completedSurveyService.getCompletedSurveys(pageable, search);
    }

    @PutMapping("/change-status")
    public CompletedSurveyDTO updateSurveyStatus(@RequestBody UpdateSurveyStatusDTO dto) {
        return completedSurveyService.changeStatus(dto);
    }
}
