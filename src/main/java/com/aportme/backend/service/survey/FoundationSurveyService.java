package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.FoundationSurveyDTO;
import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.repository.FoundationSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoundationSurveyService {

    private final FoundationSurveyRepository foundationSurveyRepository;
    private final ModelMapper modelMapper;

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

    public FoundationSurveyDTO convertToFoundationSurveyDTO(FoundationSurvey foundationSurvey) {
        List<SurveyQuestionDTO> activeQuestions = convertSurveyQuestion(foundationSurvey);

        FoundationSurveyDTO dto = mapToFoundationSurveyDTO(foundationSurvey);
        dto.setActiveQuestions(activeQuestions);
        return dto;
    }

    public FoundationSurveyDTO mapToFoundationSurveyDTO(FoundationSurvey foundationSurvey) {
        return modelMapper.map(foundationSurvey, FoundationSurveyDTO.class);
    }

    public FoundationSurvey findByFoundation(Foundation foundation) {
        return foundationSurveyRepository.findByFoundation(foundation)
                .orElseThrow(() -> new EntityNotFoundException("Foundation survey not found"));
    }

    public FoundationSurvey save(FoundationSurvey foundationSurvey) {
        return foundationSurveyRepository.save(foundationSurvey);
    }

    private List<SurveyQuestionDTO> convertSurveyQuestion(FoundationSurvey foundationSurvey) {
        return foundationSurvey.getSurveyQuestions()
                .stream()
                .filter(question -> question.getQuestionStatus() == QuestionStatus.ACTIVE)
                .map(activeQuestion -> modelMapper.map(activeQuestion, SurveyQuestionDTO.class))
                .collect(Collectors.toList());
    }
}
