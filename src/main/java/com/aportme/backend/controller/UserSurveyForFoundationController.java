package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.UpdateSurveyStatusDTO;
import com.aportme.backend.entity.dto.UserSurveyForFoundationDTO;
import com.aportme.backend.service.survey.UserSurveyForFoundationService;
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
@RequestMapping("/api/foundations/user-surveys")
public class UserSurveyForFoundationController {

    private final UserSurveyForFoundationService userSurveyService;

    @GetMapping
    @ApiOperation(value = "Return all user surveys for foundation", response = UserSurveyForFoundationDTO.class)
    public Page<UserSurveyForFoundationDTO> getUserSurveysForFoundation(
            @RequestParam(required = false) String search,
            @SortDefault(value = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return userSurveyService.getSurveysForFoundation(pageable, search);
    }

    @PutMapping("/change-status")
    public UserSurveyForFoundationDTO updateSurveyStatus(@RequestBody UpdateSurveyStatusDTO dto) {
        return userSurveyService.changeStatus(dto);
    }
}
