package com.aportme.backend.service;

import com.aportme.backend.entity.dto.pet.UpdatePetDTO;
import com.aportme.backend.entity.dto.picture.AddPetPictureDTO;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.exception.PetNotFoundException;
import com.aportme.backend.exception.PetPictureNotFoundException;
import com.aportme.backend.repository.PetRepository;
import com.aportme.backend.repository.PictureRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    public ResponseEntity<Object> upload(Long petId, List<String> base64Pictures) {
        Pet pet = petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        List<PetPicture> pictures = base64Pictures
                .stream()
                .map(base64Picture -> createUploadedPicture(pet, base64Picture))
                .collect(Collectors.toList());

        pictureRepository.saveAll(pictures);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> setProfilePicture(Long id) {
        PetPicture petPicture = findPetPictureById(id);
        List<PetPicture> pictures = pictureRepository.findAllByPet(petPicture.getPet());
        pictures.forEach(picture -> picture.setIsProfilePicture(false));
        pictureRepository.saveAll(pictures);
        petPicture.setIsProfilePicture(true);
        pictureRepository.save(petPicture);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void deleteAllPetPictures(Pet pet) {
        List<PetPicture> petPictures = getAllPicturesByPet(pet);
        pictureRepository.deleteAll(petPictures);
    }

    private PetPicture findPetPictureById(Long id) {
        return pictureRepository.findById(id).orElseThrow(PetPictureNotFoundException::new);
    }

    private PetPicture createUploadedPicture(Pet pet, String pictureInBase64) {
        PetPicture dbPicture = new PetPicture();
        dbPicture.setPictureInBase64(pictureInBase64);
        dbPicture.setIsProfilePicture(false);
        dbPicture.setPet(pet);
        return dbPicture;
    }

    private PetPicture createPictureForPet(Pet pet, AddPetPictureDTO petPictureDTO) {
        PetPicture petPicture = modelMapper.map(petPictureDTO, PetPicture.class);
        petPicture.setPet(pet);
        return petPicture;
    }

    public void delete(List<Long> ids) {
        List<PetPicture> pictures = pictureRepository.findAllById(ids);
        pictureRepository.deleteAll(pictures);
    }

    private List<PetPicture> getAllPicturesByPet(Pet pet) {
        return pictureRepository.findAllByPet(pet);
    }

    private List<PetPicture> getAllPicturesByIds(List<Long> ids) {
        return pictureRepository.findAllByIdIn(ids);
    }

    public void updatePetPictures(UpdatePetDTO updatePet, Pet pet) {
        Long newProfilePictureId = updatePet.getNewProfilePictureId();
        Long oldProfilePictureId = updatePet.getPreviousProfilePictureId();

        changeProfilePictureFromExistingOnes(newProfilePictureId, oldProfilePictureId);
        if (updatePet.getPictureIdsToDelete().size() > 0) {
            deleteAllByIds(updatePet.getPictureIdsToDelete());
        }
        if (updatePet.getPicturesToAdd().size() > 0) {
            createPicturesForPet(pet, updatePet.getPicturesToAdd());
        }
    }

    private void changeProfilePictureFromExistingOnes(Long newProfilePictureId, Long oldProfilePictureId) {
        if (newProfilePictureId != null) {
            if (!oldProfilePictureId.equals(newProfilePictureId)) {
                changeProfilePicture(oldProfilePictureId, newProfilePictureId);
            }
        } else {
            unsetProfilePicture(oldProfilePictureId);
        }
    }

    @Transactional
    public void changeProfilePicture(Long oldProfilePictureId, Long newProfilePictureId) {
        unsetProfilePicture(oldProfilePictureId);
        PetPicture newPicture = findPetPictureById(newProfilePictureId);
        newPicture.setIsProfilePicture(Boolean.TRUE);
        pictureRepository.save(newPicture);
    }

    private void unsetProfilePicture(Long oldProfilePictureId) {
        PetPicture oldPicture = findPetPictureById(oldProfilePictureId);
        oldPicture.setIsProfilePicture(Boolean.FALSE);
        pictureRepository.save(oldPicture);
    }

    public void deleteAllByIds(List<Long> ids) {
        List<PetPicture> petPictures = getAllPicturesByIds(ids);
        pictureRepository.deleteAll(petPictures);
    }

    public List<PetPicture> createPicturesForPet(Pet pet, List<AddPetPictureDTO> picturesDTO) {
        List<PetPicture> pictures = picturesDTO
                .stream()
                .map(pictureDTO -> createPictureForPet(pet, pictureDTO))
                .collect(Collectors.toList());
        return pictureRepository.saveAll(pictures);
    }
}
