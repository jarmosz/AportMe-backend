package com.aportme.backend.entity.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.enums.FoundationSurveyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class FoundationSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FoundationSurveyStatus surveyStatus = FoundationSurveyStatus.INACTIVE;

    @OneToOne
    private Foundation foundation;

    @OrderBy
    @OneToMany(mappedBy = "foundationSurvey")
    private List<SurveyQuestion> surveyQuestions = new ArrayList<>();
}
