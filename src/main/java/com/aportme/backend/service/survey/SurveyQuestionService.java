package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.dto.SurveyQuestionDTO;
import com.aportme.backend.entity.dto.survey.AddSurveyQuestionDTO;
import com.aportme.backend.entity.enums.QuestionType;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.repository.survey.SurveyQuestionRepository;
import com.aportme.backend.service.FoundationService;
import com.aportme.backend.service.PetService;
import com.aportme.backend.service.SelectValueService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurveyQuestionService {

    private final SurveyQuestionRepository surveyQuestionRepository;
    private final FoundationService foundationService;
    private final SelectValueService selectValueService;
    private final SurveyAnswerService surveyAnswerService;
    private final PetService petService;
    private final ModelMapper modelMapper;

    public List<SurveyQuestionDTO> getQuestions(Long petId) {
        Foundation foundation = getPetOrLoggedFoundation(petId);
       
        return surveyQuestionRepository.findAllByFoundation(foundation)
                .stream()
                .map(q -> modelMapper.map(q, SurveyQuestionDTO.class))
                .collect(Collectors.toList());
    }

    private Foundation getPetOrLoggedFoundation(Long petId) {
        if(petId != null) {
            Pet pet = petService.findById(petId);
            return pet.getFoundation();
        } else {
            return foundationService.findByLoggedEmail();
        }
    }

    @Transactional
    public void createQuestions(List<AddSurveyQuestionDTO> questions) {
        Foundation foundation = foundationService.findByLoggedEmail();
        questions.forEach(q -> saveSurveyQuestion(foundation, q));
    }

    public void deleteById(Long id) {
        SurveyQuestion question = findById(id);
        surveyAnswerService.deleteAllByQuestion(question);
        selectValueService.deleteAllByQuestion(question);
        surveyQuestionRepository.delete(question);
    }

    public SurveyQuestion findById(Long id) {
        return surveyQuestionRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey question not found"));
    }

    private void saveSurveyQuestion(Foundation foundation, AddSurveyQuestionDTO questionDTO) {
        SurveyQuestion question = modelMapper.map(questionDTO, SurveyQuestion.class);
        question.setFoundation(foundation);
        question = surveyQuestionRepository.save(question);
        if (questionDTO.getType().equals(QuestionType.SELECT)) {
            selectValueService.save(question, questionDTO.getValues());
        }
    }
}
