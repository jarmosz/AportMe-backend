package com.aportme.backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class ConfirmResetPasswordEmailBuilderService {

    private final TemplateEngine templateEngine;

    public String build(String confirmationUrl) {
        Context context = new Context();
        context.setVariable("confirmationUrl", confirmationUrl);
        return templateEngine.process("resetPasswordEmailTemplate", context);
    }
}
