package com.szymek.socializr.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, Object> {

    /*
        (?=.*[A-Z])         an upper case letter must occur at least once
        (?=.*[@#$%^&+=])    a special character must occur at least once
        (?=.*[0-9])         a digit must occur at least once
        (?=.*[a-z])         a lower case letter must occur at least once
        (?=\\S+$)           no whitespace allowed in the entire string
        .{8,}               at least 8 characters
    */
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";

    @Override
    public void initialize(PasswordStrength passwordMatches) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return validatePassword((String) o);
    }

    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}