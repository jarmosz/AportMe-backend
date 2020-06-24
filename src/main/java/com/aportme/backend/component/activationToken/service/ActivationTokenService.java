package com.aportme.backend.component.activationToken.service;

import com.aportme.backend.component.activationToken.entity.ActivationToken;
import com.aportme.backend.component.activationToken.repository.ActivationTokenRepository;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.event.SetActiveUserEvent;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ActivationTokenService {

    private ActivationTokenRepository activationTokenRepository;
    private ApplicationEventPublisher eventPublisher;

    public void saveToken(User user, String token){
        ActivationToken activationToken = new ActivationToken();
        activationToken.setUser(user);
        activationToken.setToken(token);
        activationToken.setExpiryDate(new DateTime().plusMinutes(1440));
        activationTokenRepository.save(activationToken);
    }

    public boolean confirmActivationToken(String token){
        Optional<ActivationToken> activationTokenFromDB = activationTokenRepository.findActivationTokenByToken(token);

        if(!activationTokenFromDB.isEmpty()){
            ActivationToken activationToken = activationTokenFromDB.get();
            if(new DateTime().isBefore(activationToken.getExpiryDate())) {
                Long activeUserId = activationToken.getUser().getId();
                eventPublisher.publishEvent(new SetActiveUserEvent(activeUserId));
                activationTokenRepository.delete(activationToken);
                return true;
            }
        }
        return false;
    }
}
