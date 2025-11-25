package org.example.mutantes.service;

public class MutantDetector {
    private static final int SEQ = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null) return false;
        int n = dna.length;
        char[][] m = new char[n][n];
        for (int i = 0; i < n; i++) m[i] = dna[i].toCharArray();

        int count = 0;
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                char base = m[r][c];
                if (base == 0) continue;
                // horizontal
                if (c <= n - SEQ) {
                    if (m[r][c+1] == base && m[r][c+2] == base && m[r][c+3] == base) {
                        count++;
                        if (count > 1) return true;
                    }
                }
                // vertical
                if (r <= n - SEQ) {
                    if (m[r+1][c] == base && m[r+2][c] == base && m[r+3][c] == base) {
                        count++;
                        if (count > 1) return true;
                    }
                }
                // diag desc
                if (r <= n - SEQ && c <= n - SEQ) {
                    if (m[r+1][c+1] == base && m[r+2][c+2] == base && m[r+3][c+3] == base) {
                        count++;
                        if (count > 1) return true;
                    }
                }
                // diag asc
                if (r >= SEQ - 1 && c <= n - SEQ) {
                    if (m[r-1][c+1] == base && m[r-2][c+2] == base && m[r-3][c+3] == base) {
                        count++;
                        if (count > 1) return true;
                    }
                }
            }
        }
        return false;
    }
}
