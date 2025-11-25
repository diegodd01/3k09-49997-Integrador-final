package org.example.mutantes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {
    private static final Pattern VALID = Pattern.compile("^[ATCG]+$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0) return false;
        int n = value.length;
        if (n < 4) return false;
        for (String row : value) {
            if (row == null) return false;
            if (row.length() != n) return false;
            if (!VALID.matcher(row).matches()) return false;
        }
        return true;
    }
}
