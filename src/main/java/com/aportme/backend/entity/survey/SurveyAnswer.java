package com.aportme.backend.entity.survey;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class SurveyAnswer {

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
