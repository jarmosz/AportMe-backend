package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.FoundationSurveyDTO;
import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.entity.enums.FoundationSurveyStatus;
import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.exception.UserSurveyWithoutDecisionException;
import com.aportme.backend.repository.FoundationSurveyRepository;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.FoundationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoundationSurveyService {

    private final FoundationSurveyRepository foundationSurveyRepository;
    private final AuthenticationService authenticationService;
    private final UserSurveyService userSurveyService;
    private final ModelMapper modelMapper;
    private FoundationService foundationService;

    public FoundationSurveyDTO getMySurvey() {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);
        FoundationSurvey foundationSurvey = findByFoundation(foundation);

        return convertToFoundationSurveyDTO(foundationSurvey);
    }


    public FoundationSurvey findOrCreateFoundationSurvey(Foundation foundation) {
        FoundationSurvey foundationSurvey = findByFoundationOrElseNull(foundation);
        if (foundationSurvey == null) {
            foundationSurvey = new FoundationSurvey();
            foundationSurvey.setFoundation(foundation);
            return save(foundationSurvey);
        }
        return foundationSurvey;
    }

    private FoundationSurvey findByFoundationOrElseNull(Foundation foundation) {
        return foundationSurveyRepository.findByFoundation(foundation).orElse(null);
    }

    @Transactional
    public void changeStatus() {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);

        changeFoundationSurveyStatusToOpposite(foundation);
    }

    private FoundationSurveyDTO convertToFoundationSurveyDTO(FoundationSurvey foundationSurvey) {
        List<SurveyQuestionDTO> activeQuestions = foundationSurvey.getSurveyQuestions()
                .stream()
                .filter(question -> question.getQuestionStatus().equals(QuestionStatus.ACTIVE))
                .map(activeQuestion -> modelMapper.map(activeQuestion, SurveyQuestionDTO.class))
                .collect(Collectors.toList());

        FoundationSurveyDTO dto = modelMapper.map(foundationSurvey, FoundationSurveyDTO.class);
        dto.setActiveQuestions(activeQuestions);
        return dto;
    }

    private void changeFoundationSurveyStatusToOpposite(Foundation foundation) {
        boolean isAtLeastOneUserSurveyWithoutDecision = userSurveyService.isAnyUserSurveyWithoutDecision(foundation);
        if (isAtLeastOneUserSurveyWithoutDecision) {
            throw new UserSurveyWithoutDecisionException();
        } else {
            FoundationSurvey foundationSurvey = foundation.getFoundationSurvey();
            FoundationSurveyStatus status = foundationSurvey.getSurveyStatus();

            if (status.equals(FoundationSurveyStatus.ACTIVE)) {
                foundationSurvey.setSurveyStatus(FoundationSurveyStatus.INACTIVE);
            } else {
                foundationSurvey.setSurveyStatus(FoundationSurveyStatus.ACTIVE);
            }
            save(foundationSurvey);
        }
    }

    private FoundationSurvey save(FoundationSurvey foundationSurvey) {
        return foundationSurveyRepository.save(foundationSurvey);
    }

    private FoundationSurvey findByFoundation(Foundation foundation) {
        return foundationSurveyRepository.findByFoundation(foundation)
                .orElseThrow(() -> new EntityNotFoundException("Foundation survey not found"));
    }

    @Autowired
    public void setFoundationService(FoundationService foundationSurvey) {
        this.foundationService = foundationSurvey;
    }
}
