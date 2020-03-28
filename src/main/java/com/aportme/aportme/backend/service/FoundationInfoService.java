package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.repository.FoundationInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoundationInfoService {

    private final FoundationInfoRepository foundationInfoRepository;

    public List<FoundationInfo> getAll() {
        return foundationInfoRepository.findAll();
    }

    public FoundationInfo getFoundationById(Long id) {
        return foundationInfoRepository.findById(id).orElse(null);
    }

    public FoundationInfo getFoundationByPetId(Long petId) {
        return foundationInfoRepository.getFoundationInfoByPetId(petId).orElse(null);
    }
}
