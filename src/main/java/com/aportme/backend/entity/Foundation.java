package com.aportme.backend.entity;

import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.entity.survey.UserSurvey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Foundation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nip;

    private String krs;

    private String accountNumber;

    private String phoneNumber;

    private String description;

    @Lob
    private String foundationLogo;

    @OneToOne
    private User user;

    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "foundation")
    private List<UserSurvey> userSurveys;

    @OneToOne
    private FoundationSurvey foundationSurvey;

    @OneToMany(
            mappedBy = "foundation",
            cascade = CascadeType.ALL
    )
    private List<Pet> pets = new ArrayList<>();
}
