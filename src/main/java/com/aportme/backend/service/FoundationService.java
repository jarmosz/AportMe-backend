package com.aportme.backend.service;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.foundation.LoggedFoundationDataDTO;
import com.aportme.backend.entity.dto.foundation.UpdateFoundationDTO;
import com.aportme.backend.repository.FoundationRepository;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class FoundationService {

    private final FoundationRepository foundationRepository;
    private final AuthenticationService authenticationService;
    private final CanonicalService canonicalService;
    private final ModelMapper modelMapper;

    public Page<FoundationDTO> getAll(String searchCityQuery, Pageable pageable) {
        ModelMapperUtil.mapFoundationEmail(modelMapper);

        searchCityQuery = canonicalService.replaceCanonicalLetters(searchCityQuery);
        Page<Foundation> page = foundationRepository.findAllWithSearch(pageable, searchCityQuery.toLowerCase());
        List<FoundationDTO> content = page
                .getContent()
                .stream()
                .map(foundation -> modelMapper.map(foundation, FoundationDTO.class))
                .collect(Collectors.toList());

        return PaginationService.mapToPageImpl(content, pageable, page.getTotalElements());
    }

    public FoundationDTO getById(Long id) {
        ModelMapperUtil.mapFoundationEmail(modelMapper);
        Foundation foundation = findById(id);
        return modelMapper.map(foundation, FoundationDTO.class);
    }

    public UpdateFoundationDTO update(UpdateFoundationDTO foundationDTO) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = findByEmail(email);
        modelMapper.map(foundationDTO, foundation);
        foundation = foundationRepository.save(foundation);
        return modelMapper.map(foundation, UpdateFoundationDTO.class);
    }

    public Foundation findById(Long id) {
        return foundationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Foundation not found"));
    }

    public Foundation findByEmail(String email) {
        return foundationRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Foundation not found"));
    }

    public LoggedFoundationDataDTO getMyData() {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = findByEmail(email);
        return modelMapper.map(foundation, LoggedFoundationDataDTO.class);
    }

    public <T> T mapTo(Object src, Class<T> dest) {
        return modelMapper.map(src, dest);
    }

    public Foundation save(Foundation foundation) {
        return foundationRepository.save(foundation);
    }
}
