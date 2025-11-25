package org.example.mutantes.controller;

import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.service.MutantService;
import org.example.mutantes.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Validated
public class MutantController {
    private final MutantService mutantService;
    private final StatsService statsService;

    public MutantController(MutantService mutantService, StatsService statsService) {
        this.mutantService = mutantService;
        this.statsService = statsService;
    }
    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "service", "Mutantes API",
                "status", "running",
                "endpoints", List.of("/mutant", "/stats")
        );
    }





    @PostMapping("/mutant")
    @Operation(summary = "Detecta si es mutante")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Es mutante"),
        @ApiResponse(responseCode = "403", description = "No es mutante"),
        @ApiResponse(responseCode = "400", description = "Request inválido")
    })
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.analyze(request.getDna());
        if (isMutant) return ResponseEntity.ok().build();
        else return ResponseEntity.status(403).build();
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> stats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
