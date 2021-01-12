package com.aportme.backend.event;

import com.aportme.backend.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDate;

@Getter
@Setter
public class SendResetPasswordLinkEvent extends ApplicationEvent {

    public SendResetPasswordLinkEvent(User user, LocalDate resetPasswordTime) {
        super(user);
        this.user = user;
        this.resetPasswordTime = resetPasswordTime;
    }

    private User user;

    private LocalDate resetPasswordTime;
}