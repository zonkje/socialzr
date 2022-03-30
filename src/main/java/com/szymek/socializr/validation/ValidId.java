package com.szymek.socializr.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Positive;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IdValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Positive
public @interface ValidId {

    public String message() default "object ID not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String entity();

}
