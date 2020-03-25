package com.aportme.aportme.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/foundation")
public class FoundationController {

    @GetMapping("/getPets")
    public String getPets() {
        return "How how";
    }
}
