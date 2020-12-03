package com.aportme.backend.service.survey;

import com.aportme.backend.entity.survey.SelectValue;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.repository.survey.SelectValueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SelectValueService {

    private final SelectValueRepository selectValueRepository;

    public List<SelectValue> convertAndSave(SurveyQuestion question, List<String> selectValues) {
        List<SelectValue> values = mapToSelectValue(question, selectValues);
        return selectValueRepository.saveAll(values);
    }

    public void deleteAllByQuestion(SurveyQuestion question) {
        List<SelectValue> values = findAllByQuestion(question);
        selectValueRepository.deleteAll(values);
    }

    public List<SelectValue> findAllByQuestion(SurveyQuestion question) {
        return selectValueRepository.findAllByQuestion(question);
    }

    private List<SelectValue> mapToSelectValue(SurveyQuestion question, List<String> selectValues) {
        return selectValues
                .stream()
                .map(SelectValue::new)
                .peek(val -> val.setQuestion(question))
                .collect(Collectors.toList());
    }
}
