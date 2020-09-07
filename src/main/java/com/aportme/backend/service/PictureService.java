package com.aportme.backend.service;

import com.aportme.backend.entity.dto.picture.AddPetPictureDTO;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.picture.PetPictureDTO;
import com.aportme.backend.exception.PetPictureNotFoundException;
import com.aportme.backend.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private PetService petService;


    public List<PetPictureDTO> getPicturesByPetId(Long id) {
        Pet pet = petService.findPetById(id);
        return pictureRepository.findAllByPet(pet)
                .stream()
                .map(picture -> modelMapper.map(picture, PetPictureDTO.class))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Object> upload(Long petId, List<String> base64Pictures) {
        Pet pet = petService.findPetById(petId);
        List<PetPicture> pictures = base64Pictures
                .stream()
                .map(base64Picture -> createUploadedPicture(pet, base64Picture))
                .collect(Collectors.toList());

        pictureRepository.saveAll(pictures);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<PetPicture> createPicturesForNewPet(Pet pet, List<AddPetPictureDTO> picturesDTO) {
        List<PetPicture> pictures = picturesDTO
                .stream()
                .map(pictureDTO -> createPictureForNewPet(pet, pictureDTO))
                .collect(Collectors.toList());
        return pictureRepository.saveAll(pictures);
    }

    public ResponseEntity<Object> setProfilePicture(Long id) {
        PetPicture newProfilePicture = findPetPictureById(id);
        Pet pet = newProfilePicture.getPet();
        PetPicture oldProfilePicture = findProfilePictureByPet(pet);

        switchProfilePicture(oldProfilePicture, newProfilePicture);
        pictureRepository.saveAll(Arrays.asList(newProfilePicture, oldProfilePicture));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void deleteAllPetPictures(Pet pet) {
        List<PetPicture> petPictures = findAllPicturesByPet(pet);
        pictureRepository.deleteAll(petPictures);
    }

    private PetPicture createUploadedPicture(Pet pet, String pictureInBase64) {
        PetPicture dbPicture = new PetPicture();
        dbPicture.setPictureInBase64(pictureInBase64);
        dbPicture.setIsProfilePicture(false);
        dbPicture.setPet(pet);
        return dbPicture;
    }

    private PetPicture createPictureForNewPet(Pet pet, AddPetPictureDTO petPictureDTO) {
        PetPicture petPicture = modelMapper.map(petPictureDTO, PetPicture.class);
        petPicture.setPet(pet);
        return petPicture;
    }

    public PetPictureDTO createPicture(Long petId, String base64Picture) {
        Pet pet = petService.findPetById(petId);
        PetPicture petPicture = new PetPicture(base64Picture, false, pet);
        petPicture = pictureRepository.save(petPicture);
        return modelMapper.map(petPicture, PetPictureDTO.class);
    }

    public void deletePictureById(Long id) {
        pictureRepository.deleteById(id);
    }

    private PetPicture findPetPictureById(Long id) {
        return pictureRepository.findById(id).orElseThrow(PetPictureNotFoundException::new);
    }

    private List<PetPicture> findAllPicturesByPet(Pet pet) {
        return pictureRepository.findAllByPet(pet);
    }

    private PetPicture findProfilePictureByPet(Pet pet) {
        return pictureRepository.findByPetAndIsProfilePictureTrue(pet).orElseThrow(PetPictureNotFoundException::new);
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
