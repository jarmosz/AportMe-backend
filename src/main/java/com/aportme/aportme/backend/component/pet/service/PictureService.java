package com.aportme.aportme.backend.component.pet.service;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.pet.dto.pictures.AddPetPictureDTO;
import com.aportme.aportme.backend.component.pet.dto.pictures.PetPictureDTO;
import com.aportme.aportme.backend.component.pet.entity.Pet;
import com.aportme.aportme.backend.component.pet.entity.PetPicture;
import com.aportme.aportme.backend.component.pet.repository.PetRepository;
import com.aportme.aportme.backend.component.pet.repository.PictureRepository;
import com.aportme.aportme.backend.utils.dto.EntityDTOConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final PetRepository petRepository;
    private final EntityDTOConverter entityDTOConverter;

    public DTOEntity add(Long petId, AddPetPictureDTO pictureDTO) throws Exception {
        PetPicture dbPicture = new PetPicture();
        dbPicture.setPictureInBase64(pictureDTO.getPictureInBase64());
        dbPicture.setIsProfilePicture(pictureDTO.getIsProfilePicture());

        Optional<Pet> petFromDb = petRepository.findById(petId);
        if (petFromDb.isEmpty()) {
            throw new Exception("Pet not found");
        }

        dbPicture.setPet(petFromDb.get());
        return entityDTOConverter.convertToDto(pictureRepository.save(dbPicture), new PetPictureDTO());
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

    public void delete(Long id) {
        pictureRepository.deleteById(id);
    }
}
