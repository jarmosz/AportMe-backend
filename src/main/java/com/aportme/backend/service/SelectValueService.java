package com.aportme.backend.service;

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

    public void save(SurveyQuestion question, List<String> selectValues) {
        List<SelectValue> values = selectValues
                .stream()
                .map(SelectValue::new)
                .peek(val -> val.setQuestion(question))
                .collect(Collectors.toList());

        selectValueRepository.saveAll(values);
    }

    public void deleteAllByQuestion(SurveyQuestion question) {
        List<SelectValue> values = selectValueRepository.findAllByQuestion(question);
        selectValueRepository.deleteAll(values);
    }
}
