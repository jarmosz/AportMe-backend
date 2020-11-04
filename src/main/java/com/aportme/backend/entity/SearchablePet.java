package com.aportme.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchablePet {
    private String breed;
    private String name;
}
