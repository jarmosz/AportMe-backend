package com.aportme.backend.component.user.listener;

import com.aportme.backend.component.user.event.SetActiveUserEvent;
import com.aportme.backend.component.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SetActiveUserListener implements ApplicationListener<SetActiveUserEvent> {

    private UserService userService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(SetActiveUserEvent setActiveUserEvent) {
        userService.setActiveUserFlag(setActiveUserEvent.getActiveUserId());
    }
}
