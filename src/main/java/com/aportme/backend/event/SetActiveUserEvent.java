package com.aportme.backend.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SetActiveUserEvent extends ApplicationEvent {

    private Long activeUserId;

    public SetActiveUserEvent(Long activeUserId) {
        super(activeUserId);
        this.activeUserId = activeUserId;
    }
}
