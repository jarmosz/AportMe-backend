package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.pet.pictures.AddPetPictureDTO;
import com.aportme.aportme.backend.dto.pet.pictures.PetPictureDTO;
import com.aportme.aportme.backend.entity.pet.Pet;
import com.aportme.aportme.backend.entity.pet.PetPicture;
import com.aportme.aportme.backend.repository.PetRepository;
import com.aportme.aportme.backend.repository.PictureRepository;
import com.aportme.aportme.backend.utils.EntityDTOConverter;
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

        Optional<Pet> petFromDb = petRepository.findById(petId);
        if (petFromDb.isEmpty()) {
            throw new Exception("Pet not found");
        }

        dbPicture.setPet(petFromDb.get());
        return entityDTOConverter.convertToDto(pictureRepository.save(dbPicture), new PetPictureDTO());
    }

    List<PetPicture> createAll(List<AddPetPictureDTO> picturesDTO) {
        List<PetPicture> dbPictures = new ArrayList<>();
        picturesDTO.forEach(pictureDTO -> {
                    PetPicture dbPicture = new PetPicture();
                    dbPicture.setPictureInBase64(pictureDTO.getPictureInBase64());
                    dbPictures.add(dbPicture); });
        return pictureRepository.saveAll(dbPictures);
    }

    public void delete(Long id) {
        pictureRepository.deleteById(id);
    }
}
