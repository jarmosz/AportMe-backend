package com.aportme.backend.service;

import com.aportme.backend.entity.ActivationToken;
import com.aportme.backend.entity.User;
import com.aportme.backend.event.OnRegistrationCompleteEvent;
import com.aportme.backend.event.SetActiveUserEvent;
import com.aportme.backend.exception.UserIsAlreadyActivatedException;
import com.aportme.backend.exception.UserNotFoundException;
import com.aportme.backend.repository.ActivationTokenRepository;
import com.aportme.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActivationTokenService {

    private ActivationTokenRepository activationTokenRepository;
    private ApplicationEventPublisher eventPublisher;
    private UserRepository userRepository;

    public void saveToken(User user, String token) {
        ActivationToken activationToken = activationTokenRepository.findByUserId(user.getId()).orElse(null);

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
        URI redirectAddress = URI.create("http://localhost:8081/accountNotActivated");

        if (activationTokenFromDB.isPresent()) {
            ActivationToken activationToken = activationTokenFromDB.get();
            if (new DateTime().isBefore(activationToken.getExpiryDate())) {
                Long activeUserId = activationToken.getUser().getId();
                eventPublisher.publishEvent(new SetActiveUserEvent(activeUserId));
                activationTokenRepository.delete(activationToken);
                redirectAddress = URI.create("http://localhost:8081/accountActivationComplete");
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
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
