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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final AuthenticationService authenticationService;
    private final SearchPetRepository searchPetRepository;
    private final CanonicalService canonicalService;
    private final UserService userService;

    public Page<Pet> findPetsByFilters(Pageable pageable, PetFilters filters) {
        SearchablePet searchablePet = resolveSearchQuery(filters.getSearchBreedQuery(), filters.getSearchNameQuery());
        Long userId = authenticationService.getLoggedUserId();
        Boolean shouldShowOnlyLikedPets = verifyUserCall(filters.getOnlyLikedPets(), userId);
        filters.setOnlyLikedPets(shouldShowOnlyLikedPets);

        User user = getUserForSearch(filters);
        return searchPetRepository.findByFilters(
                pageable,
                searchablePet.getName(),
                searchablePet.getBreed(),
                filters,
                user);
    }

    public String prepareSearchableField(String query) {
        query = query == null || query.isBlank() ? "" : query.toLowerCase().trim();
        return canonicalService.replaceCanonicalLetters(query);
    }

    private SearchablePet resolveSearchQuery(String searchBreedQuery, String searchNameQuery) {
        return new SearchablePet(prepareSearchableField(searchBreedQuery), prepareSearchableField(searchNameQuery));
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
