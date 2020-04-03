package com.aportme.aportme.backend.component.foundation.service;

import com.aportme.aportme.backend.component.address.service.AddressService;
import com.aportme.aportme.backend.component.foundation.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.component.foundation.entity.FoundationInfo;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.component.foundation.dto.AddFoundationDTO;
import com.aportme.aportme.backend.component.foundation.dto.FoundationInfoDTO;
import com.aportme.aportme.backend.component.foundation.dto.UpdateFoundationDTO;
import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.component.user.repository.UserRepository;
import com.aportme.aportme.backend.utils.dto.EntityDTOConverter;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoundationInfoService {

    private final FoundationInfoRepository foundationInfoRepository;
    private final UserRepository userRepository;
    private final EntityDTOConverter entityDTOConverter;
    private final AddressService addressService;

    public List<DTOEntity> getAll() {
        return foundationInfoRepository.findAll()
                .stream()
                .map((foundationInfo -> entityDTOConverter.convertToDto(foundationInfo, new FoundationInfoDTO())))
                .collect(Collectors.toList());
    }

    public DTOEntity getById(Long id) {
        Optional<FoundationInfo> foundationInfoFromDB = foundationInfoRepository.findById(id);
        if (foundationInfoFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(foundationInfoFromDB.get(), new FoundationInfoDTO());
    }

    public DTOEntity getByPetId(Long petId) {
        Optional<FoundationInfo> foundationInfoFromDB = foundationInfoRepository.findByPetId(petId);
        if (foundationInfoFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(foundationInfoFromDB.get(), new FoundationInfoDTO());
    }

    public DTOEntity update(Long id, UpdateFoundationDTO foundationInfoDTO) throws Exception {
        Optional<FoundationInfo> foundationInfoFromDB = foundationInfoRepository.findById(id);
        if (foundationInfoFromDB.isEmpty()) {
            throw new Exception("FoundationInfo not found");
        }
        FoundationInfo dbFoundationInfo = foundationInfoFromDB.get();
        FoundationInfo convertedDTO = (FoundationInfo) entityDTOConverter.convertToEntity(new FoundationInfo(), foundationInfoDTO);
        UtilsService.copyNonNullProperties(convertedDTO, dbFoundationInfo);
        return entityDTOConverter.convertToDto(foundationInfoRepository.save(dbFoundationInfo), new FoundationInfoDTO());
    }

    public DTOEntity create(Long userId, AddFoundationDTO foundationInfoDTO) throws Exception {
        FoundationInfo dbFoundationInfo = new FoundationInfo();
        dbFoundationInfo.setName(foundationInfoDTO.getName());
        dbFoundationInfo.setNip(foundationInfoDTO.getNip());
        dbFoundationInfo.setPhoneNumber(foundationInfoDTO.getPhoneNumber());
        dbFoundationInfo.setAddress(addressService.create(foundationInfoDTO.getAddress()));

        Optional<User> userFromDB = userRepository.findById(userId);
        if (userFromDB.isEmpty()) {
            throw new Exception("User not found");
        }

        dbFoundationInfo.setUser(userFromDB.get());
        return entityDTOConverter.convertToDto(foundationInfoRepository.save(dbFoundationInfo), new FoundationInfoDTO());
    }
}
