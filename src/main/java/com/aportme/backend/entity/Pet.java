package com.aportme.backend.entity;

import com.aportme.backend.entity.enums.*;
import com.aportme.backend.entity.survey.Survey;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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

    private String name;

    private String searchableName;

    private String breed;

    private String searchableBreed;

    private PetSex sex;

    private int age;

    private PetType petType;

    private AgeSuffix ageSuffix;

    private AgeCategory ageCategory;

    private PetSize size;

    @Column(length = 512)
    private String diseases;

    @Column(length = 512)
    private String behaviorToChildren;

    @Column(length = 512)
    private String behaviorToAnimals;

    private Boolean trainingNeeded;

    private Boolean behavioristNeeded;

    @Column(length = 1024)
    private String description;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "pet")
    private List<Survey> surveys;

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
