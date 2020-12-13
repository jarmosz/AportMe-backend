package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.dto.foundation.LoggedFoundationDataDTO;
import com.aportme.backend.entity.dto.foundation.UpdateFoundationDTO;
import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.repository.FoundationRepository;
import com.aportme.backend.service.survey.FoundationSurveyService;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final FoundationSurveyService foundationSurveyService;

    public Page<FoundationDTO> getAll(String searchCityQuery, Pageable pageable) {
        String searchedCity = searchService.prepareSearchableField(searchCityQuery);
        Page<Foundation> foundations = foundationRepository.findAllByAddress_SearchableCityContains(pageable, searchedCity);
        List<FoundationDTO> foundationDTOs = foundations
                .getContent()
                .stream()
                .map(foundation -> modelMapper.map(foundation, FoundationDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(foundationDTOs, pageable, foundations.getTotalElements());
    }

    public FoundationDTO getById(Long id) {
        Foundation foundation = findById(id);
        ModelMapperUtil.mapFoundationEmail(modelMapper);
        return modelMapper.map(foundation, FoundationDTO.class);
    }

    public UpdateFoundationDTO update(UpdateFoundationDTO foundationDTO) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = findByEmail(email);
        modelMapper.map(foundationDTO, foundation);
        foundation = foundationRepository.save(foundation);
        return modelMapper.map(foundation, UpdateFoundationDTO.class);
    }

    public void create(Long userId, AddFoundationDTO addFoundationDTO) {
        Foundation foundation = modelMapper.map(addFoundationDTO, Foundation.class);
        Address address = addressService.save(addFoundationDTO.getAddress());
        User user = userService.findById(userId);

        foundation.setUser(user);
        foundation.setAddress(address);
        foundation = foundationRepository.save(foundation);

        FoundationSurvey foundationSurvey = foundationSurveyService.findOrCreateFoundationSurvey(foundation);
        foundation.setFoundationSurvey(foundationSurvey);
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
}
