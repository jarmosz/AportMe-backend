package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.enums.AgeSuffix;
import lombok.Data;

@Data
public class SimplePetDTO {

    private Long id;

    private String name;

    private String breed;

    private int age;

    private AgeSuffix ageSuffix;

    private String profilePicture;

    private boolean isLiked = false;

    private Long foundationId;

    private String foundationLogo;

    private String foundationName;

    private String foundationCity;
}
