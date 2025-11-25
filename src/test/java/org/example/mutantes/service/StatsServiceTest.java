package org.example.mutantes.service;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatsServiceTest {
    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    public StatsServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStats_AllMutants() {
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10, response.getCount_mutant_dna());
        assertEquals(0, response.getCount_human_dna());
        assertEquals(10.0, response.getRatio());
    }

    @Test
    void testStats_AllHumans() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(20L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCount_mutant_dna());
        assertEquals(20, response.getCount_human_dna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testStats_MixedValues() {
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(40, response.getCount_mutant_dna());
        assertEquals(100, response.getCount_human_dna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    void testStats_NoData() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(0, response.getCount_mutant_dna());
        assertEquals(0, response.getCount_human_dna());
        assertEquals(0.0, response.getRatio());
    }

    @Test
    void testStats_RatioRoundedCorrectly() {
        when(repository.countByIsMutant(true)).thenReturn(3L);
        when(repository.countByIsMutant(false)).thenReturn(7L);

        StatsResponse response = statsService.getStats();

        assertEquals(3, response.getCount_mutant_dna());
        assertEquals(7, response.getCount_human_dna());
        assertEquals(0.428571, response.getRatio(), 0.000001);
    }

    @Test
    void testStats_RepositoryCalledCorrectly() {
        when(repository.countByIsMutant(true)).thenReturn(5L);
        when(repository.countByIsMutant(false)).thenReturn(10L);

        statsService.getStats();

        verify(repository, times(1)).countByIsMutant(true);
        verify(repository, times(1)).countByIsMutant(false);
    }
}
