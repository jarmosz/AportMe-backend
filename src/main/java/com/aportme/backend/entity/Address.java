package com.aportme.backend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String city;

    private String searchableCity;

    @Size(max = 100)
    private String street;

    @Size(min = 1, max = 6)
    private String houseNumber;

    @Size(max = 6)
    private String flatNumber;

    @Size(max = 6)
    @Pattern(regexp = "^\\d{2}-\\d{3}$")
    private String zipCode;
}
