package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.picture.AddPetPictureDTO;
import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.entity.dto.picture.UploadPictureDTO;
import com.aportme.backend.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private PetService petService;


    public List<PetPictureDTO> getById(Long id) {
        Pet pet = petService.findById(id);
        return pictureRepository.findAllByPet(pet)
                .stream()
                .map(picture -> modelMapper.map(picture, PetPictureDTO.class))
                .collect(Collectors.toList());
    }

    public List<PetPicture> createPicturesForNewPet(Pet pet, List<AddPetPictureDTO> picturesDTO) {
        List<PetPicture> pictures = picturesDTO
                .stream()
                .map(pictureDTO -> createPictureForNewPet(pet, pictureDTO))
                .collect(Collectors.toList());
        return pictureRepository.saveAll(pictures);
    }

    public void setProfilePicture(Long id) {
        PetPicture newProfilePicture = findById(id);
        Pet pet = newProfilePicture.getPet();
        PetPicture oldProfilePicture = findProfilePictureByPet(pet);

        switchProfilePicture(oldProfilePicture, newProfilePicture);
        pictureRepository.saveAll(Arrays.asList(newProfilePicture, oldProfilePicture));
    }

    public void deleteAll(Pet pet) {
        List<PetPicture> petPictures = findAllByPet(pet);
        pictureRepository.deleteAll(petPictures);
    }

    public PetPicture findProfilePicture(List<PetPicture> pictures) {
        return pictures.stream()
                .filter(PetPicture::getIsProfilePicture)
                .findFirst()
                .orElse(pictures.get(0));
    }

    private PetPicture createPictureForNewPet(Pet pet, AddPetPictureDTO dto) {
        PetPicture petPicture = new PetPicture();
        String encodedString = encodePictureToBase64(dto);

        petPicture.setPictureInBase64(encodedString);
        petPicture.setIsProfilePicture(dto.getIsProfilePicture());
        petPicture.setPet(pet);

        return petPicture;
    }

    private String encodePictureToBase64(AddPetPictureDTO dto) {
        try {
            byte[] fileContent = dto.getPicture().getBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException ex) {
            throw new InvalidParameterException("A");
        }
    }

    public PetPictureDTO createPicture(Long petId, UploadPictureDTO pictureDTO) {
        Pet pet = petService.findById(petId);
        PetPicture petPicture = new PetPicture(pictureDTO.getBase64Picture(), false, pet);
        pictureRepository.save(petPicture);

        return modelMapper.map(petPicture, PetPictureDTO.class);
    }

    public void deletePictureById(Long id) {
        pictureRepository.deleteById(id);
    }

    private PetPicture findById(Long id) {
        return pictureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet picture not found"));
    }

    private List<PetPicture> findAllByPet(Pet pet) {
        return pictureRepository.findAllByPet(pet);
    }

    private PetPicture findProfilePictureByPet(Pet pet) {
        return pictureRepository.findByPetAndIsProfilePictureTrue(pet)
                .orElseThrow(() -> new EntityNotFoundException("Pet picture not found"));
    }

    private void switchProfilePicture(PetPicture oldPicture, PetPicture newPicture) {
        oldPicture.setIsProfilePicture(false);
        newPicture.setIsProfilePicture(true);
    }

    @Autowired
    public void setPetService(PetService petService) {
        this.petService = petService;
    }

}
