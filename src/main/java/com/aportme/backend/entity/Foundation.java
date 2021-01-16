package com.aportme.backend.entity;

import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.entity.survey.UserSurvey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Foundation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    private String name;

    @Size(max = 10)
    private String nip;

    @Size(max = 10)
    private String krs;

    @Size(max = 45)
    private String accountNumber;

    @Size(max = 15)
    private String phoneNumber;

    @Column(columnDefinition="text", length = 1024)
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
