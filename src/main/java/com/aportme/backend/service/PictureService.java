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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public ResponseEntity<Object> setProfilePicture(Long id) {
        PetPicture newProfilePicture = findById(id);
        Pet pet = newProfilePicture.getPet();
        PetPicture oldProfilePicture = findProfilePictureByPet(pet);

        switchProfilePicture(oldProfilePicture, newProfilePicture);
        pictureRepository.saveAll(Arrays.asList(newProfilePicture, oldProfilePicture));

        return new ResponseEntity<>(HttpStatus.OK);
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

    public PetPictureDTO createPicture(Long petId, UploadPictureDTO pictureDTO) {
        Pet pet = petService.findById(petId);
        PetPicture petPicture = new PetPicture(pictureDTO.getBase64Picture(), false, pet);
        petPicture = pictureRepository.save(petPicture);
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
