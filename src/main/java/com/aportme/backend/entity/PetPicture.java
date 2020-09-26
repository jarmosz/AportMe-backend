package com.aportme.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class PetPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @NonNull
    private String pictureInBase64;

    @NonNull
    private Boolean isProfilePicture;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
