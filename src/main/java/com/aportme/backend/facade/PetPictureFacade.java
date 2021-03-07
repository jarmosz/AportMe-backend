package com.aportme.backend.facade;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.picture.ChangeProfilePictureDTO;
import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.entity.dto.picture.UploadPictureDTO;
import com.aportme.backend.exception.UnableToDeleteFirebaseResource;
import com.aportme.backend.service.FirebaseStorageService;
import com.aportme.backend.service.PetPictureService;
import com.aportme.backend.service.PetService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class PetPictureFacade {

    private final PetPictureService petPictureService;
    private final FirebaseStorageService firebaseStorageService;
    private final PetService petService;
    private final ModelMapper modelMapper;

    public List<PetPictureDTO> findPetsPictureByPetId(Long id) {
        Pet pet = petService.findById(id);
        return petPictureService.findAllByPet(pet)
                .stream()
                .map(picture -> modelMapper.map(picture, PetPictureDTO.class))
                .collect(Collectors.toList());
    }

    public PetPictureDTO addPicture(Long petId, UploadPictureDTO dto) {
        Pet pet = petService.findById(petId);
        PetPicture picture = petPictureService.createPicture(dto, pet);

        return modelMapper.map(picture, PetPictureDTO.class);
    }


    public void deletePicture(Long id) {
        PetPicture picture = petPictureService.findById(id);
        String fileName = picture.getFileName();

        boolean result = firebaseStorageService.delete(fileName);
        if (!result) {
            throw new UnableToDeleteFirebaseResource("Unable to delete pet picture from storage");
        }
        petPictureService.deleteById(id);
    }

    public PetPictureDTO changeProfilePicture(ChangeProfilePictureDTO dto) {
        PetPicture newProfilePicture = petPictureService.findById(dto.getNewProfilePictureId());
        PetPicture oldProfilePicture = petPictureService.findById(dto.getOldProfilePictureId());

        oldProfilePicture.setIsProfilePicture(false);
        newProfilePicture.setIsProfilePicture(true);
        petPictureService.saveAll(Arrays.asList(newProfilePicture, oldProfilePicture));

        return modelMapper.map(newProfilePicture, PetPictureDTO.class);
    }
}
