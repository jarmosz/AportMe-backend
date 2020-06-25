package com.aportme.backend.component.pet.service;

import com.aportme.backend.component.pet.dto.pictures.AddPetPictureDTO;
import com.aportme.backend.component.pet.dto.pictures.PetPictureDTO;
import com.aportme.backend.component.pet.entity.Pet;
import com.aportme.backend.component.pet.entity.PetPicture;
import com.aportme.backend.component.pet.repository.PetRepository;
import com.aportme.backend.component.pet.repository.PictureRepository;
import com.aportme.backend.utils.dto.DTOEntity;
import com.aportme.backend.utils.dto.EntityDTOConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final EntityDTOConverter entityDTOConverter;
    private final PetRepository petRepository;

    public List<DTOEntity> upload(Long petId, List<String> base64Pictures) {
        List<DTOEntity> addedPictures = new ArrayList<>();
        try {
            Pet petFromDb = getPetFromDB(petId);
            base64Pictures.forEach(base64Picture -> {
                PetPicture dbPicture = new PetPicture();
                dbPicture.setPictureInBase64(base64Picture);
                dbPicture.setIsProfilePicture(false);
                dbPicture.setPet(petFromDb);
                addedPictures.add(entityDTOConverter.convertToDto(pictureRepository.save(dbPicture), new PetPictureDTO()));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addedPictures;
    }

    List<PetPicture> createAll(Pet pet, List<AddPetPictureDTO> picturesDTO) {
        List<PetPicture> dbPictures = new ArrayList<>();
        picturesDTO.forEach(pictureDTO -> {
            PetPicture dbPicture = new PetPicture();
            dbPicture.setPictureInBase64(pictureDTO.getPictureInBase64());
            dbPicture.setIsProfilePicture(pictureDTO.getIsProfilePicture());
            dbPicture.setPet(pet);
            dbPictures.add(dbPicture);
        });
        return pictureRepository.saveAll(dbPictures);
    }

    public DTOEntity setProfilePicture(Long id) throws Exception {
        PetPicture petPicture = getPictureFromDB(id);
        pictureRepository.findAllByPet(petPicture.getPet()).forEach(picture -> {
            picture.setIsProfilePicture(false);
            pictureRepository.save(picture);
        });
        petPicture.setIsProfilePicture(true);

        return entityDTOConverter.convertToDto(pictureRepository.save(petPicture), new PetPictureDTO());
    }

    public void delete(List<Long> ids) {
        ids.forEach(pictureRepository::deleteById);
    }

    private PetPicture getPictureFromDB(Long id) throws Exception {
        Optional<PetPicture> pictureFromDb = pictureRepository.findById(id);
        if (pictureFromDb.isEmpty()) {
            throw new Exception("Picture not found");
        }
        return pictureFromDb.get();
    }

    private Pet getPetFromDB(Long id) throws Exception {
        Optional<Pet> petFromDb = petRepository.findById(id);
        if(petFromDb.isEmpty()) {
            throw new Exception("Pet not found");
        }
        return petFromDb.get();
    }
}
