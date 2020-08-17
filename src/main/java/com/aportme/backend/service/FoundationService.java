package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.foundation.UpdateFoundationDTO;
import com.aportme.backend.exception.FoundationNotFoundException;
import com.aportme.backend.repository.FoundationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoundationService {

    private final FoundationRepository foundationRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    public Page<FoundationDTO> getAllFoundations(Pageable pageable) {
        Page<Foundation> foundationsInfo = foundationRepository.findAll(pageable);
        List<FoundationDTO> foundationsInfoDTOs = foundationsInfo
                .getContent()
                .stream()
                .map(foundationInfo -> modelMapper.map(foundationInfo, FoundationDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(foundationsInfoDTOs, pageable, foundationsInfo.getTotalElements());
    }

    public FoundationDTO getFoundationById(Long id) {
        Foundation foundation = findFoundationById(id);
        return modelMapper.map(foundation, FoundationDTO.class);
    }

    public FoundationDTO getFoundationByPetId(Long petId) {
        Foundation foundation = foundationRepository.findByPetId(petId).orElseThrow(FoundationNotFoundException::new);
        return modelMapper.map(foundation, FoundationDTO.class);
    }

    public ResponseEntity<Object> updateFoundation(Long id, UpdateFoundationDTO foundationInfoDTO) {
        Foundation foundation = foundationRepository.findById(id).orElseThrow(FoundationNotFoundException::new);
        modelMapper.map(foundationInfoDTO, foundation);
        foundationRepository.save(foundation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> createFoundation(Long userId, AddFoundationDTO addFoundationDTO) {
        Foundation foundation = modelMapper.map(addFoundationDTO, Foundation.class);
        Address address = addressService.createAddress(addFoundationDTO.getAddress());
        User user = userService.findUserById(userId);

        foundation.setUser(user);
        foundation.setAddress(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> uploadFoundationLogo(Long id, String base64Logo) {
        Foundation foundation = findFoundationById(id);
        foundation.setFoundationLogo(base64Logo);
        foundationRepository.save(foundation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Foundation findFoundationById(Long id) {
        return foundationRepository.findById(id).orElseThrow(FoundationNotFoundException::new);
    }
}
