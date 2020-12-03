package com.aportme.backend.entity.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.enums.SurveyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class UserSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SurveyStatus status = SurveyStatus.SUBMITTED;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToMany(mappedBy = "userSurvey")
    private List<SurveyAnswer> answers;
}
