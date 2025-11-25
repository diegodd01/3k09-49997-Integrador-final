package org.example.mutantes.service;

import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Optional;

@Service
public class MutantService {
    private final DnaRecordRepository repo;
    private final MutantDetector detector = new MutantDetector();

    public MutantService(DnaRecordRepository repo) {
        this.repo = repo;
    }

    public boolean analyze(String[] dna) {
        String hash = sha256(String.join("", dna));
        Optional<DnaRecord> cached = repo.findByDnaHash(hash);
        if (cached.isPresent()) return cached.get().isMutant();

        boolean isMutant = detector.isMutant(dna);
        DnaRecord rec = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(isMutant)
                .createdAt(Instant.now())
                .build();
        repo.save(rec);
        return isMutant;
    }

    private String sha256(String input) {
        try { MessageDigest md = MessageDigest.getInstance("SHA-256"); byte[] h = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : h) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
