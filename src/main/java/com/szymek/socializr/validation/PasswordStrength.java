package com.szymek.socializr.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Documented
public @interface PasswordStrength {

    String message() default "The password must contain at least 8 characters and at least 1 upper case letter," +
            " 1 lower case letter, 1 numeric character and 1 special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
