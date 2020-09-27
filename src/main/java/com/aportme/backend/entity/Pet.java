package com.aportme.backend.entity;

import com.aportme.backend.entity.enums.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String breed;

    private PetSex sex;

    private int age;

    private PetType petType;

    private AgeSuffix ageSuffix;

    private AgeCategory ageCategory;

    private PetSize size;

    private String diseases;

    private String behaviorToChildren;

    private String behaviorToAnimals;

    private Boolean trainingNeeded;

    private Boolean behavioristNeeded;

    @Lob
    private String description;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @JsonIgnoreProperties("pets")
    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private Foundation foundation;

    @OneToMany(mappedBy = "pet",
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH}
    )
    private List<PetPicture> pictures = new ArrayList<>();

    @ManyToMany(mappedBy = "likedPets")
    private List<User> users = new ArrayList<>();
}