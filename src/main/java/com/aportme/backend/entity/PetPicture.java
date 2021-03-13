package com.aportme.backend.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class PetPicture {

    @Builder
    public PetPicture(String fileName, boolean isProfilePicture, Pet pet) {
        this.fileName = fileName;
        this.isProfilePicture = isProfilePicture;
        this.pet = pet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String fileName;

    @Lob
    @NonNull
    private String downloadUrl;

    @NonNull
    private Boolean isProfilePicture;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
