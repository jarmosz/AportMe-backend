package com.aportme.backend.facade;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.survey.FoundationSurveyDTO;
import com.aportme.backend.entity.enums.FoundationSurveyStatus;
import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.FoundationService;
import com.aportme.backend.service.survey.FoundationSurveyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
@AllArgsConstructor
public class FoundationSurveyFacade {

    private final AuthenticationService authenticationService;
    private final FoundationService foundationService;
    private final FoundationSurveyService foundationSurveyService;

    public FoundationSurveyDTO getLoggedFoundationSurvey() {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);
        FoundationSurvey foundationSurvey = foundationSurveyService.findByFoundation(foundation);

        return foundationSurveyService.convertToFoundationSurveyDTO(foundationSurvey);
    }

    public void changeStatus() {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);

        FoundationSurvey foundationSurvey = foundation.getFoundationSurvey();
        FoundationSurveyStatus newStatus = getNewStatus(foundationSurvey);
        foundationSurvey.setSurveyStatus(newStatus);
        foundationSurveyService.save(foundationSurvey);
    }

    private FoundationSurveyStatus getNewStatus(FoundationSurvey survey) {
        FoundationSurveyStatus status = survey.getSurveyStatus();
        return status == FoundationSurveyStatus.ACTIVE ? FoundationSurveyStatus.INACTIVE : FoundationSurveyStatus.ACTIVE;
    }

}
