package com.aportme.backend.component.activationToken.service;

import com.aportme.backend.component.activationToken.entity.ActivationToken;
import com.aportme.backend.component.activationToken.event.OnRegistrationCompleteEvent;
import com.aportme.backend.component.activationToken.repository.ActivationTokenRepository;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.event.SetActiveUserEvent;
import com.aportme.backend.component.user.exception.UserIsAlreadyActivatedException;
import com.aportme.backend.component.user.exception.UserNotFoundException;
import com.aportme.backend.component.user.repository.UserRepository;
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
        Optional<ActivationToken> activationTokenFromDB = activationTokenRepository.findByUserId(user.getId());
        ActivationToken activationToken;

        if (activationTokenFromDB.isPresent()) {
            activationToken = activationTokenFromDB.get();
        } else {
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
        Optional<User> userFromDB = userRepository.findByEmail(email);

        if (userFromDB.isPresent()) {
            User user = userFromDB.get();
            if (!user.isActive()) {
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, DateTime.now()));
            } else {
                throw new UserIsAlreadyActivatedException();
            }
        } else {
            throw new UserNotFoundException();
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
