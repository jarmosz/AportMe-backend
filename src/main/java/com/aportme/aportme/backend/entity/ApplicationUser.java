package com.aportme.aportme.backend.entity;

import com.aportme.aportme.backend.security.Role;
import lombok.*;

import javax.persistence.*;

@Entity(name = "user_tbl")
@Data
@Getter
@Setter
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String email;

    private String password;

    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private Role role;
}


