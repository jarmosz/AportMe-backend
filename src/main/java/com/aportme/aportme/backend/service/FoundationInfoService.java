package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.foundation.AddFoundationDTO;
import com.aportme.aportme.backend.dto.foundation.FoundationInfoDTO;
import com.aportme.aportme.backend.dto.foundation.UpdateFoundationDTO;
import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.entity.user.User;
import com.aportme.aportme.backend.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.repository.UserRepository;
import com.aportme.aportme.backend.utils.EntityDTOConverter;
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

    public FoundationInfo create(Long userId, AddFoundationDTO foundationInfoDTO) throws Exception {
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
        return foundationInfoRepository.save(dbFoundationInfo);
    }
}
