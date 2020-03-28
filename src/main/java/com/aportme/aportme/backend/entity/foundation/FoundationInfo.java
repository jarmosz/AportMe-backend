package com.aportme.aportme.backend.entity.foundation;

import com.aportme.aportme.backend.entity.Address;
import com.aportme.aportme.backend.entity.pet.Pet;
import com.aportme.aportme.backend.entity.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToOne
    private User user;

    @OneToOne
    private Address address;

    @OneToMany(
            mappedBy = "foundationInfo",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Pet> pets;
}
