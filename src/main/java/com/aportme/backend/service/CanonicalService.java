package com.aportme.backend.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CanonicalService {
    private static final Map<String, String> canonicalRules  = new HashMap<>() {{
        put("ą", "a");
        put("ć", "c");
        put("ę", "e");
        put("ł", "l");
        put("ń", "n");
        put("ó", "o");
        put("ś", "s");
        put("ż", "z");
        put("ź", "z");
    }};

    public String replaceCanonicalLetters(String query) {
        for (String key : canonicalRules.keySet()) {
            query = query.replaceAll(key, canonicalRules.get(key));
        }
        return query;
    }
}
