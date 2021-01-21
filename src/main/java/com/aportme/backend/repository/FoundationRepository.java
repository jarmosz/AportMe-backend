package com.aportme.backend.repository;

import com.aportme.backend.entity.Foundation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> {

    @Query("SELECT f FROM Foundation f INNER JOIN users u ON u.id = f.user.id WHERE u.email = ?1")
    Optional<Foundation> findByEmail(String email);

    @Query(value = "select f from Foundation f " +
            "inner join f.address a " +
            "where lower(a.searchableCity) like %:search% or " +
            "lower(a.city) like %:search% or " +
            "lower(f.searchableName) like %:search% or " +
            "lower(f.name) like %:search% " +
            "order by f.name asc")
    Page<Foundation> findAllWithSearch(Pageable pageable, @Param("search") String search);
}
