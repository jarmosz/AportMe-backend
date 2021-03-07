package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.dto.picture.PictureBaseDTO;
import com.aportme.backend.repository.PictureRepository;
import com.google.api.client.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PetPictureService {

    private final FirebaseStorageService firebaseStorageService;
    private final PictureRepository pictureRepository;

    public PetPicture createPicture(PictureBaseDTO dto, Pet pet) {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        byte[] content = Base64.decodeBase64(dto.getBase64Picture());

        PetPicture picture = PetPicture.builder()
                .fileName(fileName)
                .isProfilePicture(dto.getIsProfilePicture())
                .pet(pet)
                .build();

        String downloadUrl = firebaseStorageService.upload(fileName, "image/jpeg", content);

        picture.setDownloadUrl(downloadUrl);
        return save(picture);
    }

    public void deleteAll(Pet pet) {
        List<PetPicture> petPictures = findAllByPet(pet);
        petPictures.forEach(pic -> firebaseStorageService.delete(pic.getFileName()));
        pictureRepository.deleteAll(petPictures);
    }

    public PetPicture findProfilePicture(List<PetPicture> pictures) {
        return pictures.stream()
                .filter(PetPicture::getIsProfilePicture)
                .findFirst()
                .orElse(pictures.get(0));
    }

    public PetPicture save(PetPicture picture) {
        return pictureRepository.save(picture);
    }

    public void deleteById(Long id) {
        PetPicture petPicture = findById(id);
        firebaseStorageService.delete(petPicture.getFileName());
        pictureRepository.delete(petPicture);
    }

    public void saveAll(List<PetPicture> pictures) {
        pictureRepository.saveAll(pictures);
    }

    public PetPicture findById(Long id) {
        return pictureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pet picture not found"));
    }

    public List<PetPicture> findAllByPet(Pet pet) {
        return pictureRepository.findAllByPet(pet);
    }
}
