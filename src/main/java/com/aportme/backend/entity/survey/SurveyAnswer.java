package com.aportme.backend.entity.survey;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class SurveyAnswer {

    @Builder
    public SurveyAnswer(String answer, SurveyQuestion question, Survey survey) {
        this.answer = answer;
        this.question = question;
        this.survey = survey;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    @OneToOne
    private SurveyQuestion question;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

}
