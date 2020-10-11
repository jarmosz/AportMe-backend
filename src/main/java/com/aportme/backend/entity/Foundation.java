package com.aportme.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Foundation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String nip;

    private String krs;

    private String accountNumber;

    private String phoneNumber;

    @Lob
    private String description;

    @Lob
    private String foundationLogo;

    @OneToOne
    private User user;

    @OneToOne
    private Address address;

    @OneToMany(
            mappedBy = "foundation",
            cascade = CascadeType.ALL
    )
    private List<Pet> pets = new ArrayList<>();
}
