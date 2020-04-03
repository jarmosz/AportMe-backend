package com.aportme.aportme.backend.component.userInfo.entity;

import com.aportme.aportme.backend.component.address.entity.Address;
import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.component.pet.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private String phoneNumber;

    @OneToOne
    private Address address;

    @OneToOne
    private User user;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "userInfo_pet",
            joinColumns = @JoinColumn(name = "userInfo_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> likedPets = new ArrayList<>();
}
