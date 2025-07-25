package org.example.springbootmanagementservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileTypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileConstraint {
    String message() default "Only PDF files are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
