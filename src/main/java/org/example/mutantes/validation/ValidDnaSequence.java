package org.example.mutantes.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ValidDnaSequenceValidator.class })
public @interface ValidDnaSequence {
    String message() default "Invalid DNA sequence: must be NxN with only A,T,C,G and minimum size 4";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
