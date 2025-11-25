package org.example.mutantes.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.mutantes.validation.ValidDnaSequence;

@Data
public class DnaRequest {
    @NotNull
    @NotEmpty
    @ValidDnaSequence
    private String[] dna;
}
