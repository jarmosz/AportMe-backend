package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.foundation.LoggedFundationDataDTO;
import com.aportme.backend.entity.dto.foundation.UpdateFoundationDTO;
import com.aportme.backend.repository.FoundationRepository;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoundationService {

    private final FoundationRepository foundationRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;
    private final SearchService searchService;

    public Page<FoundationDTO> getAll(String searchCityQuery, Pageable pageable) {
        Page<Foundation> foundationsInfo = foundationRepository
                    .findAllByAddress_SearchableCityContains(pageable, searchService.prepareSearchableField(searchCityQuery));
        List<FoundationDTO> foundationsInfoDTOs = foundationsInfo
                .getContent()
                .stream()
                .map(foundationInfo -> modelMapper.map(foundationInfo, FoundationDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(foundationsInfoDTOs, pageable, foundationsInfo.getTotalElements());
    }

    public FoundationDTO getById(Long id) {
        Foundation foundation = findById(id);
        ModelMapperUtil.mapFoundationEmail(modelMapper);
        return modelMapper.map(foundation, FoundationDTO.class);
    }

    public ResponseEntity<Object> update(UpdateFoundationDTO foundationDTO) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = findByEmail(email);
        modelMapper.map(foundationDTO, foundation);
        foundationRepository.save(foundation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> create(Long userId, AddFoundationDTO addFoundationDTO) {
        Foundation foundation = modelMapper.map(addFoundationDTO, Foundation.class);
        Address address = addressService.create(addFoundationDTO.getAddress());
        User user = userService.findById(userId);

        foundation.setUser(user);
        foundation.setAddress(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Foundation findByLoggedEmail() {
        String foundationEmail = authenticationService.getLoggedUsername();
        return foundationRepository.findByEmail(foundationEmail).orElseThrow(() -> new EntityNotFoundException("Foundation not found"));
    }

    public Foundation findById(Long id) {
        return foundationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Foundation not found"));
    }

    public Foundation findByEmail(String email) {
        return foundationRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Foundation not found"));
    }

    public LoggedFundationDataDTO getMyData() {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = findByEmail(email);
        return modelMapper.map(foundation, LoggedFundationDataDTO.class);
    }
}
