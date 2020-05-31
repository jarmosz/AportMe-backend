package com.aportme.backend.component.pet.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class PetPicture {

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
