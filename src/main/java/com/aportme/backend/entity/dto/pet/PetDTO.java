package com.aportme.backend.entity.dto.pet;

import com.aportme.backend.entity.dto.foundation.FoundationForPetDTO;
import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.entity.dto.user.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PetDTO extends PetBaseDTO {

    private Long id;

    private boolean isLiked;

    private FoundationForPetDTO foundation;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private List<PetPictureDTO> pictures = new ArrayList<>();

    private List<UserDTO> users = new ArrayList<>();
}
