package com.aportme.backend.component.activationToken.event;

import com.aportme.backend.component.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String appUrl;
    private DateTime registrationTime;
    private User user;

    public OnRegistrationCompleteEvent(User user, DateTime registrationTime, String appUrl) {
        super(user);
        this.user = user;
        this.registrationTime = registrationTime;
        this.appUrl = appUrl;
    }
}
