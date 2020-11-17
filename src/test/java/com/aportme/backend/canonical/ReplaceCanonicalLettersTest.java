package com.aportme.backend.canonical;

import com.aportme.backend.service.CanonicalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ReplaceCanonicalLettersTest {

    @InjectMocks
    CanonicalService canonicalService;

    @Test
    public void replaceACanonicalLetter() {
        String query = "ą";
        assertEquals("a", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceFewACanonicalLetters() {
        String query = "ąąą";
        assertEquals("aaa", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceCCanonicalLetter() {
        String query = "ć";
        assertEquals("c", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceFewCCanonicalLetters() {
        String query = "ććć";
        assertEquals("ccc", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceLCanonicalLetter() {
        String query = "ł";
        assertEquals("l", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceFewLCanonicalLetters() {
        String query = "łłł";
        assertEquals("lll", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceNCanonicalLetter() {
        String query = "ń";
        assertEquals("n", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceFewNCanonicalLetters() {
        String query = "ńńń";
        assertEquals("nnn", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceOCanonicalLetter() {
        String query = "ó";
        assertEquals("o", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceFewOCanonicalLetters() {
        String query = "óóó";
        assertEquals("ooo", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceSCanonicalLetter() {
        String query = "ś";
        assertEquals("s", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceFewSCanonicalLetters() {
        String query = "śśś";
        assertEquals("sss", canonicalService.replaceCanonicalLetters(query));
    }

    @Test
    public void replaceZCanonicalLetter() {
        String query = "ż";
        String query2 = "ż";
        assertEquals("z", canonicalService.replaceCanonicalLetters(query));
        assertEquals("z", canonicalService.replaceCanonicalLetters(query2));
    }

    @Test
    public void replaceFewZCanonicalLetters() {
        String query = "żżż";
        String query2 = "źźź";
        assertEquals("zzz", canonicalService.replaceCanonicalLetters(query));
        assertEquals("zzz", canonicalService.replaceCanonicalLetters(query2));
    }

    @Test
    public void replaceDifferentCanonicalLetters() {
        String query = "ąćęłńóśżź";
        assertEquals("acelnoszz", canonicalService.replaceCanonicalLetters(query));
    }
}
