package com.aportme.backend.event;

import com.aportme.backend.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SendResetPasswordLinkEvent extends ApplicationEvent {

    private DateTime resetPasswordTime;
    private User user;

    public SendResetPasswordLinkEvent(User user, DateTime resetPasswordTime) {
        super(user);
        this.user = user;
        this.resetPasswordTime = resetPasswordTime;
    }
}