package com.aportme.aportme.backend.entity.pet;

import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.entity.pet.enums.AgeCategory;
import com.aportme.aportme.backend.entity.pet.enums.AgeSuffix;
import com.aportme.aportme.backend.entity.pet.enums.PetSize;
import com.aportme.aportme.backend.entity.pet.enums.PetType;
import com.aportme.aportme.backend.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String breed;

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

    private String description;

    @ManyToOne
    @JoinColumn(name = "foundation_info_id")
    private FoundationInfo foundationInfo;

    @OneToMany(mappedBy = "pet",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<PetPicture> pictures = new ArrayList<>();

    @ManyToMany(mappedBy = "likedPets")
    private List<User> users = new ArrayList<>();
}
