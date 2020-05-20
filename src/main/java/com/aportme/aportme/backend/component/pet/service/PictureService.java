package com.aportme.aportme.backend.component.pet.service;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.pet.dto.pictures.PetPictureDTO;
import com.aportme.aportme.backend.component.pet.entity.Pet;
import com.aportme.aportme.backend.component.pet.entity.PetPicture;
import com.aportme.aportme.backend.component.pet.repository.PetRepository;
import com.aportme.aportme.backend.component.pet.repository.PictureRepository;
import com.aportme.aportme.backend.utils.dto.EntityDTOConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final EntityDTOConverter entityDTOConverter;
    private final PetRepository petRepository;

    public List<DTOEntity> upload(Long petId, MultipartFile[] pictures) {
        List<DTOEntity> addedPictures = new ArrayList<>();
        for (MultipartFile picture : pictures) {
            PetPicture dbPicture = new PetPicture();

            try {
                dbPicture.setPictureInBase64(Base64.getEncoder().encodeToString(picture.getBytes()));
                dbPicture.setIsProfilePicture(false);
                dbPicture.setPet(getPetFromDB(petId));
                addedPictures.add(entityDTOConverter.convertToDto(pictureRepository.save(dbPicture), new PetPictureDTO()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return addedPictures;
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
