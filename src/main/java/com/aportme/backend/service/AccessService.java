package com.aportme.backend.service;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.repository.PetRepository;
import com.aportme.backend.repository.survey.SurveyQuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccessService {

    private final PetService petService;
    private final AuthenticationService authenticationService;
    private final SurveyQuestionRepository surveyQuestionRepository;

    public Boolean isAdmin() {
        return authenticationService.getAuthorities().contains(Role.ADMIN);
    }

    public Boolean isFoundation() {
        return authenticationService.getAuthorities().contains(Role.FOUNDATION);
    }

    public Boolean isUser() {
        return authenticationService.getAuthorities().contains(Role.USER);
    }

    public Boolean isFoundationPet(Long petId) {
        Pet pet = petService.findById(petId);
        String foundationEmail = pet.getFoundation().getUser().getEmail();
        return authenticationService.getLoggedUsername().equals(foundationEmail);
    }

    public Boolean isFoundationQuestion(Long questionId) {
        SurveyQuestion question = surveyQuestionRepository.findById(questionId).orElse(null);
        if (question != null) {
            return authenticationService.getLoggedUsername().equals(question.getFoundation().getUser().getEmail());
        }
        return false;
    }

    public Boolean arePetLikedByUser(Long petId) {
        Pet pet = petService.findById(petId);
        List<String> userEmails = pet.getUsers()
                .stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
        return userEmails.contains(authenticationService.getAuthentication().getName());
    }
}
