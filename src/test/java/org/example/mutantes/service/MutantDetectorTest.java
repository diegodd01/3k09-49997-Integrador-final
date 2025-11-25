package org.example.mutantes.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {
    @Test
    void detectsMutantExample() {
        String[] dna = new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
        MutantDetector d = new MutantDetector();
        assertTrue(d.isMutant(dna));
    }

    @Test
    void detectsHumanExample() {
        String[] dna = new String[]{"ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"};
        MutantDetector d = new MutantDetector();
        assertFalse(d.isMutant(dna));
    }
}
