package com.aportme.backend.entity.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.enums.QuestionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class SurveyQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    private QuestionType type;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus = QuestionStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;

    @OneToMany(mappedBy = "question")
    private List<SelectValue> selectValues = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "survey_question")
    private FoundationSurvey foundationSurvey;
}
