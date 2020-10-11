package com.aportme.backend.listener;

import com.aportme.backend.event.SetActiveUserEvent;
import com.aportme.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SetActiveUserListener implements ApplicationListener<SetActiveUserEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(SetActiveUserEvent setActiveUserEvent) {
        userService.activeUser(setActiveUserEvent.getActiveUserId());
    }
}
