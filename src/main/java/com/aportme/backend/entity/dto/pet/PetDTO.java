package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.dto.foundation.FoundationForPetDTO;
import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.entity.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PetDTO extends PetBaseDTO {

    private Long id;

    private FoundationForPetDTO foundationInfo;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private List<PetPictureDTO> pictures = new ArrayList<>();

    private List<UserDTO> users = new ArrayList<>();
}
