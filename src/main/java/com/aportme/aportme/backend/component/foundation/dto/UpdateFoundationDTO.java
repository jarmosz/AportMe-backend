package com.aportme.aportme.backend.component.foundation.dto;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class UpdateFoundationDTO implements DTOEntity {

    private String name;

    private String nip;

    private String phoneNumber;

    private String foundation;
}
