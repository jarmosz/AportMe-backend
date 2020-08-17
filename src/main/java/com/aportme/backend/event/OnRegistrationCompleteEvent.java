package com.aportme.backend.event;

import com.aportme.backend.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private DateTime registrationTime;
    private User user;

    public OnRegistrationCompleteEvent(User user, DateTime registrationTime) {
        super(user);
        this.user = user;
        this.registrationTime = registrationTime;
    }
}
