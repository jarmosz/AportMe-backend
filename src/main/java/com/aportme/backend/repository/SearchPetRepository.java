package com.aportme.backend.repository;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.Pet_;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.pet.PetFilters;
import com.aportme.backend.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchPetRepository implements CustomPetRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Pet> findByFilters(Pageable pageable, String name, String breed, PetFilters filters, User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
        Root<Pet> pet = criteriaQuery.from(Pet.class);

        criteriaQuery.where(buildPredicate(pet, criteriaBuilder, name, breed, filters, user));
        criteriaQuery.orderBy(criteriaBuilder.desc(pet.get(Pet_.creationDate)));

        TypedQuery<Pet> query = entityManager.createQuery(criteriaQuery);

        int totalRows = query.getResultList().size();

        List<Pet> content = query
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return PaginationService.mapToPageImpl(content, pageable, totalRows);
    }

    private Predicate buildPredicate(Root<Pet> pet, CriteriaBuilder criteriaBuilder, String name, String breed, PetFilters filters, User user) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.like(pet.get(Pet_.searchableName), "%" + name + "%"));
        predicates.add(criteriaBuilder.like(pet.get(Pet_.searchableBreed), "%" + breed + "%"));
        if (filters != null) {
            if (filters.getSize() != null) predicates.add(criteriaBuilder.equal(pet.get(Pet_.size), filters.getSize()));
            if (filters.getAgeCategory() != null)
                predicates.add(criteriaBuilder.equal(pet.get(Pet_.ageCategory), filters.getAgeCategory()));
            if (filters.getPetType() != null)
                predicates.add(criteriaBuilder.equal(pet.get(Pet_.petType), filters.getPetType()));
            if (filters.getPetSex() != null)
                predicates.add(criteriaBuilder.equal(pet.get(Pet_.sex), filters.getPetSex()));
            if (filters.getOnlyLikedPets() && user != null) {
                predicates.add(criteriaBuilder.isMember(user, pet.get(Pet_.USERS)));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
