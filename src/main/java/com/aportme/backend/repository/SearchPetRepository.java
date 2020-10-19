package com.aportme.backend.repository;

import com.aportme.backend.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;

@Repository
@RequiredArgsConstructor
public class SearchPetRepository implements CustomPetRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Pet> findPetsByNameAndBreed(Pageable pageable, String name, String breed) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);

        Root<Pet> pet = criteriaQuery.from(Pet.class);
        Predicate namePredicate = criteriaBuilder.like(pet.get("searchableName"), "%" + name + "%");
        Predicate breedPredicate = criteriaBuilder.like(pet.get("searchableBreed"), "%" + breed + "%");
        criteriaQuery.where(namePredicate, breedPredicate);

        TypedQuery<Pet> query = entityManager.createQuery(criteriaQuery);

        int totalRows = query.getResultList().size();
        return new PageImpl<>(query.getResultList(), pageable, totalRows);
    }
}
