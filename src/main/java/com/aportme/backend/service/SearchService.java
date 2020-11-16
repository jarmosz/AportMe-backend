package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.SearchablePet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.pet.PetFilters;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.repository.SearchPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final AuthenticationService authenticationService;
    private final SearchPetRepository searchPetRepository;
    private final UserService userService;

    public Page<Pet> findPetsByFilters(Pageable pageable, PetFilters filters, boolean isFoundationCall) {
        SearchablePet searchablePet = resolveSearchQuery(filters.getSearchBreedQuery(), filters.getSearchNameQuery());
        Long userId = authenticationService.getLoggedUserId();
        isFoundationCall = verifyFoundationCall(isFoundationCall, userId);
        filters.setOnlyLikedPets(verifyUserCall(filters.getOnlyLikedPets(), userId));
        return searchPetRepository.findByFilters(
                pageable,
                searchablePet.getName(),
                searchablePet.getBreed(),
                filters,
                isFoundationCall,
                userId,
                getUserForSearch(filters));
    }

    private SearchablePet resolveSearchQuery(String searchBreedQuery, String searchNameQuery) {
        return new SearchablePet(prepareSearchableField(searchBreedQuery), prepareSearchableField(searchNameQuery));
    }

    private String prepareSearchableField(String query) {
        return query == null || query.isBlank() ? "" : query.toLowerCase().trim();
    }

    private boolean verifyFoundationCall(boolean isFoundatioCall, Long foundationId) {
        if(isFoundatioCall) {
            return foundationId != null && authenticationService.getAuthorities().contains(Role.FOUNDATION);
        }
        return false;
    }

    private boolean verifyUserCall(Boolean onlyLikedPets, Long userId) {
        if(onlyLikedPets != null && onlyLikedPets) {
            return userId != null && authenticationService.getAuthorities().contains(Role.USER);
        }
        return false;
    }

    private User getUserForSearch(PetFilters filters) {
        return filters.getOnlyLikedPets() ? userService.getLoggedUser() : null;
    }
}
