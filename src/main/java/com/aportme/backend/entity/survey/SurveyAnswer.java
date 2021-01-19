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
    public SurveyAnswer(String answer, SurveyQuestion question, UserSurvey userSurvey) {
        this.answer = answer;
        this.question = question;
        this.userSurvey = userSurvey;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512)
    private String answer;

    @OneToOne
    private SurveyQuestion question;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private UserSurvey userSurvey;

}
