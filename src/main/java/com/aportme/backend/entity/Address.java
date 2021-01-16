package com.aportme.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(length = 100)
    private String city;

    private String searchableCity;

    @Size(max = 100)
    @Column(length = 100)
    private String street;

    @Size(min = 1, max = 6)
    @Column(length = 6)
    private String houseNumber;

    @Size(max = 6)
    @Column(length = 6)
    private String flatNumber;

    @Size(max = 6)
    @Pattern(regexp = "^\\d{2}-\\d{3}$")
    @Column(length = 6)
    private String zipCode;
}
