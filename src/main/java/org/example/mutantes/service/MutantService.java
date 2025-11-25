package org.example.mutantes.service;

import lombok.RequiredArgsConstructor;
import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.exception.DnaHashCalculationException;
import org.example.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector detector;
    private final DnaRecordRepository repository;

    /**
     * Analiza ADN, revisa cache, llama al detector y guarda el resultado.
     */
    public boolean analyzeDna(String[] dna) {
        String dnaHash = calculateDnaHash(dna);

        // 1) Buscar en cache
        Optional<DnaRecord> existing = repository.findByDnaHash(dnaHash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        // 2) Analizar con MutantDetector
        boolean isMutant = detector.isMutant(dna);

        // 3) Guardar nuevo registro
        DnaRecord record = new DnaRecord();
        record.setDnaHash(dnaHash);
        record.setMutant(isMutant);
        record.setCreatedAt(Instant.now());

        repository.save(record);

        return isMutant;
    }

    /**
     * Calcula un hash SHA-256 para representar un ADN.
     */
    public String calculateDnaHash(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", dna);
            byte[] hashBytes = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("Error calculando hash del ADN", e);
        }
    }
}