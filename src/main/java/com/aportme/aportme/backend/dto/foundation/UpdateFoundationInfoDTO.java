package com.aportme.aportme.backend.dto.foundation;

import com.aportme.aportme.backend.dto.DTOEntity;
import lombok.Data;

@Data
public class UpdateFoundationInfoDTO implements DTOEntity {

    private String name;

    private String nip;

    private String phoneNumber;

}
