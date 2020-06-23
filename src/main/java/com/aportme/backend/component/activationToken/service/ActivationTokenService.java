package com.aportme.backend.component.activationToken.service;

import org.joda.time.DateTime;

public class ActivationTokenService {

    private DateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return new DateTime().plusMinutes(expiryTimeInMinutes);
    }
}
