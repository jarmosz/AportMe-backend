package com.aportme.backend.entity;

import com.aportme.backend.entity.enums.*;
import com.aportme.backend.entity.survey.UserSurvey;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50)
    @Column(length = 50)
    private String name;

    private String searchableName;

    @Size(min = 3, max = 50)
    @Column(length = 50)
    private String breed;

    private String searchableBreed;

    private PetSex sex;

    @Min(1)
    @Max(20)
    private int age;

    private PetType petType;

    private AgeSuffix ageSuffix;

    private AgeCategory ageCategory;

    private PetSize size;

    @Column(length = 500)
    @Size(max = 500)
    private String diseases;

    @Column(length = 500)
    @Size(max = 500)
    private String behaviorToChildren;

    @Column(length = 500)
    @Size(max = 500)
    private String behaviorToAnimals;

    private Boolean trainingNeeded;

    private Boolean behavioristNeeded;

    @Column(length = 1000)
    @Size(max = 1000)
    private String description;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "pet")
    private List<UserSurvey> userSurveys;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;

    @OneToMany(
            mappedBy = "pet",
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH}
    )
    private List<PetPicture> pictures = new ArrayList<>();

    @ManyToMany(mappedBy = "likedPets")
    private List<User> users = new ArrayList<>();
}
