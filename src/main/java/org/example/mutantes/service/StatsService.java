package org.example.mutantes.service;

import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private final DnaRecordRepository repo;

    public StatsService(DnaRecordRepository repo) { this.repo = repo; }

    public StatsResponse getStats() {
        long mutants = repo.countByIsMutant(true);
        long humans = repo.countByIsMutant(false);
        double ratio = humans == 0 ? (mutants > 0 ? (double) mutants : 0.0) : ((double) mutants) / humans;
        return new StatsResponse(mutants, humans, ratio);
    }
}
