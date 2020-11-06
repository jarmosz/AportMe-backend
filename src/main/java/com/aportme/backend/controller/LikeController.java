package com.aportme.backend.controller;

import com.aportme.backend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    @PreAuthorize("@accessService.isUser()")
    @PostMapping("/{petId}")
    public ResponseEntity<Object> like(@PathVariable Long petId) {
        return likeService.like(petId);
    }

    @PreAuthorize("@accessService.isUser() && @accessService.arePetLikedByUser(#petId)")
    @DeleteMapping("/{petId}")
    public ResponseEntity<Object> unlike(@PathVariable Long petId) {
        return likeService.unlike(petId);
    }

    @PreAuthorize("@accessService.isUser()")
    @DeleteMapping("/clear")
    public ResponseEntity<Object> clearLikes() {
        return likeService.clearLikes();
    }
}
