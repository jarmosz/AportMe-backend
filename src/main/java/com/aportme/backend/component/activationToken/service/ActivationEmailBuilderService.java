package com.aportme.backend.component.activationToken.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class ActivationEmailBuilderService {

    private TemplateEngine templateEngine;

    public String build(String activationLink) {
        Context context = new Context();
        context.setVariable("activationLink", activationLink);
        return templateEngine.process("activationEmailTemplate", context);
    }
}
