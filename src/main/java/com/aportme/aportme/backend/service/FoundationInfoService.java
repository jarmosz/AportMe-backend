package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.repository.UserRepository;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoundationInfoService {

    private final FoundationInfoRepository foundationInfoRepository;
    private final UserRepository userRepository;

    public List<FoundationInfo> getAll() {
        return foundationInfoRepository.findAll();
    }

    public FoundationInfo getById(Long id) {
        return foundationInfoRepository.findById(id).orElse(null);
    }

    public FoundationInfo getByPetId(Long petId) {
        return foundationInfoRepository.findByPetId(petId).orElse(null);
    }

    public FoundationInfo update(Long id, FoundationInfo foundationInfo) {
        FoundationInfo dbFoundationInfo = foundationInfoRepository.findById(id).get();
        UtilsService.copyNonNullProperties(foundationInfo, dbFoundationInfo);
        return foundationInfoRepository.save(dbFoundationInfo);
    }

    public FoundationInfo create(Long userId, FoundationInfo foundationInfo) {
        FoundationInfo dbFoundationInfo = new FoundationInfo();
        dbFoundationInfo.setName(foundationInfo.getName());
        dbFoundationInfo.setNip(foundationInfo.getNip());
        dbFoundationInfo.setAddress(foundationInfo.getAddress());
        dbFoundationInfo.setPhoneNumber(foundationInfo.getPhoneNumber());
        dbFoundationInfo.setUser(userRepository.findById(userId).get());
        return  foundationInfoRepository.save(dbFoundationInfo);
    }
}
