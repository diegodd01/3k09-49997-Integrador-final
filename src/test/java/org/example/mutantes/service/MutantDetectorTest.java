package org.example.mutantes.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    // ========= MUTANTES =========

    @Test
    void testMutantHorizontal() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TTAT",
                "AGAC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantVertical() {
        String[] dna = {
                "ATGCGA",
                "ATGTGC",
                "ATATGT",
                "ATAAGG",
                "ACCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalPrincipal() {
        String[] dna = {
                "ATGCGA",
                "CAGAGC",
                "TTATAT",
                "AGAAAG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonalInversa() {
        String[] dna = {
                "ATGCGA",
                "CAGTAC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantTwoSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // ========= HUMANOS =========

    @Test
    void testHumanNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testHumanOneSequenceOnly() {
        String[] dna = {
                "AAAA",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }
    // ========= VALIDACIONES =========

    @Test
    void testNullDna() {
        assertFalse(detector.isMutant(null));
    }

    @Test
    void testEmptyArray() {
        String[] dna = {};
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testNonSquareMatrix() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testInvalidCharacters() {
        String[] dna = {
                "ATGX",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testRowLengthMismatch() {
        String[] dna = {
                "ATGCG",
                "CAGT",
                "TTATGT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testLowercaseInvalid() {
        String[] dna = {
                "atgc",
                "cagt",
                "ttat",
                "agac"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testBoundaryChecks() {
        String[] dna = {
                "ATGA",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testVerySmallMatrix() {
        String[] dna = {
                "AT",
                "TA"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void test4x4MutantDiagonal() {
        String[] dna = {
                "AAGT",
                "CAAT",
                "TTA A",
                "AGCA"
        };
        assertFalse(detector.isMutant(dna));
    }
}
