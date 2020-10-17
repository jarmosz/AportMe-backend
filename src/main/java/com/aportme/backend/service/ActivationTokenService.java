package com.aportme.backend.service;

import com.aportme.backend.entity.ActivationToken;
import com.aportme.backend.entity.User;
import com.aportme.backend.event.OnRegistrationCompleteEvent;
import com.aportme.backend.event.SetActiveUserEvent;
import com.aportme.backend.exception.UserIsAlreadyActivatedException;
import com.aportme.backend.exception.UserNotFoundException;
import com.aportme.backend.repository.ActivationTokenRepository;
import com.aportme.backend.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class ActivationTokenService {

    @Value("${frontendUrl}")
    private String frontendUrl;

    private final ActivationTokenRepository activationTokenRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    public ActivationTokenService(ActivationTokenRepository activationTokenRepository,
                                  ApplicationEventPublisher eventPublisher,
                                  UserRepository userRepository) {
        this.activationTokenRepository = activationTokenRepository;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;
    }

    public void saveToken(User user, String token) {
        ActivationToken activationToken = findById(user.getId());

        if (activationToken == null) {
            activationToken = new ActivationToken();
            activationToken.setUser(user);
        }
        activationToken.setToken(token);
        activationToken.setExpiryDate(new DateTime().plusMinutes(1440));

        activationTokenRepository.save(activationToken);
    }

    public ResponseEntity<Object> confirmActivationToken(String token) {
        Optional<ActivationToken> activationTokenFromDB = activationTokenRepository.findActivationTokenByToken(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        URI redirectAddress = URI.create(this.frontendUrl + "/accountNotActivated");

        if (activationTokenFromDB.isPresent()) {
            ActivationToken activationToken = activationTokenFromDB.get();
            if (new DateTime().isBefore(activationToken.getExpiryDate())) {
                Long activeUserId = activationToken.getUser().getId();
                eventPublisher.publishEvent(new SetActiveUserEvent(activeUserId));
                activationTokenRepository.delete(activationToken);
                redirectAddress = URI.create(this.frontendUrl + "/accountActivationComplete");
            }
        }
        httpHeaders.setLocation(redirectAddress);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    public ResponseEntity<Object> resendActivationToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (!user.isActive()) {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, DateTime.now()));
        } else {
            throw new UserIsAlreadyActivatedException();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ActivationToken findById(Long id) {
        return activationTokenRepository.findByUserId(id).orElse(null);
    }
}
