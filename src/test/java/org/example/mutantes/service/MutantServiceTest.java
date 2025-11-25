package org.example.mutantes.service;
import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MutantServiceTest {
    private MutantDetector detector;
    private DnaRecordRepository repository;
    private MutantService service;

    @BeforeEach
    void setup() {
        detector = mock(MutantDetector.class);
        repository = mock(DnaRecordRepository.class);
        service = new MutantService(detector, repository);
    }

    @Test
    void testReturnsCachedRecordIfExists() {
        String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };

        DnaRecord record = new DnaRecord();
        record.setDnaHash("HASH123");
        record.setMutant(true);

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(record));

        boolean result = service.analyzeDna(dna);

        assertTrue(result);
        verify(detector, never()).isMutant(any());
    }

    @Test
    void testSavesNewRecordIfNotCached() {
        String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        service.analyzeDna(dna);

        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);
        verify(repository).save(captor.capture());

        DnaRecord saved = captor.getValue();
        assertTrue(saved.isMutant());
        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void testHashChangesWhenDnaChanges() {
        String[] dna1 = { "AAAA", "CCCC", "GGGG", "TTTT" };
        String[] dna2 = { "AAA A", "CCCC", "GGGG", "TTTT" };

        String hash1 = service.calculateDnaHash(dna1);
        String hash2 = service.calculateDnaHash(dna2);

        assertNotEquals(hash1, hash2);
    }

    @Test
    void testAnalyzeDnaReturnsTrueForMutant() {
        String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(true);

        boolean result = service.analyzeDna(dna);

        assertTrue(result);
    }

    @Test
    void testAnalyzeDnaReturnsFalseForHuman() {
        String[] dna = { "ATGCGA", "CAGTGC", "TTATTT", "AGAAGG", "CCCCTA", "TCACTG" };

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(dna)).thenReturn(false);

        boolean result = service.analyzeDna(dna);

        assertFalse(result);
    }
}
