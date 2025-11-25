package org.example.mutantes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "dna_records", indexes = {
    @Index(name = "idx_dna_hash", columnList = "dnaHash"),
    @Index(name = "idx_is_mutant", columnList = "isMutant")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String dnaHash;

    @Column(nullable = false)
    private boolean isMutant;

    @Column(nullable = false)
    private Instant createdAt;
}
