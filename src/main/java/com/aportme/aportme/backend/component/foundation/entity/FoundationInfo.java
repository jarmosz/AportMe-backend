package com.aportme.aportme.backend.component.foundation.entity;

import com.aportme.aportme.backend.component.address.entity.Address;
import com.aportme.aportme.backend.component.pet.entity.Pet;
import com.aportme.aportme.backend.component.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class FoundationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nip;

    private String phoneNumber;

    private String description;

    @Lob
    private String foundationLogo;

    @OneToOne
    private User user;

    @OneToOne
    private Address address;

    @OneToMany(
            mappedBy = "foundationInfo",
            cascade = CascadeType.ALL
    )
    private List<Pet> pets = new ArrayList<>();
}
