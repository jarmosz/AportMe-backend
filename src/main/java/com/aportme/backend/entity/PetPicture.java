package com.aportme.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PetPicture {

    public PetPicture(String picture, Boolean isProfilePicture, Pet pet) {
        this.pictureInBase64 = picture;
        this.isProfilePicture = isProfilePicture;
        this.pet = pet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String pictureInBase64;

    private Boolean isProfilePicture;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
