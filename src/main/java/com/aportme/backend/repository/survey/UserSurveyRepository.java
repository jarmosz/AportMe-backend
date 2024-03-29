package com.aportme.backend.repository.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.survey.UserSurvey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSurveyRepository extends JpaRepository<UserSurvey, Long> {

    List<UserSurvey> findAllByUser(User user);

    Optional<UserSurvey> findByUserAndPet(User user, Pet pet);

    Page<UserSurvey> findAllByFoundationAndPet_SearchableNameContainingIgnoreCase(Pageable pageable, Foundation foundation, String petName);

    List<UserSurvey> findAllByPet(Pet pet);
}
