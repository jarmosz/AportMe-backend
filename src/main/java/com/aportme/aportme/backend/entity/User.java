package com.aportme.aportme.backend.entity;

import com.aportme.aportme.backend.security.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String encryptedPassword;

    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private Role role;
}


