package com.aportme.backend.entity;

import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.entity.survey.UserSurvey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @Pattern(regexp = "^\\d{10}$")
    private String nip;

    @Pattern(regexp = "^\\d{10}$")
    private String krs;

    @Pattern(regexp = "^\\d{2} \\d{4} \\d{4} \\d{4} \\d{4} \\d{4} \\d{4}$")
    private String accountNumber;

    @Pattern(regexp = "^\\d{3} \\d{3} \\d{3}$")
    private String phoneNumber;

    @Column(columnDefinition="text", length = 1000)
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
