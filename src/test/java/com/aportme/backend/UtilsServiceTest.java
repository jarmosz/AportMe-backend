package com.aportme.backend;


import com.aportme.backend.component.pet.enums.AgeCategory;
import com.aportme.backend.component.pet.enums.AgeSuffix;
import com.aportme.backend.utils.UtilsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsServiceTest {
    @Test
    void shouldReturnYoungCategory() {
        assertEquals(AgeCategory.YOUNG, UtilsService.prepareAgeCategory(5, AgeSuffix.MONTHS));
    }

    @Test
    void shouldReturnNormalCategory() {
        assertEquals(AgeCategory.NORMAL, UtilsService.prepareAgeCategory(2, AgeSuffix.YEARS));
        assertEquals(AgeCategory.NORMAL, UtilsService.prepareAgeCategory(7, AgeSuffix.MONTHS));
    }

    @Test
    void shouldReturnSeniorCategory() {
        assertEquals(AgeCategory.SENIOR, UtilsService.prepareAgeCategory(4, AgeSuffix.YEARS));
    }
}
