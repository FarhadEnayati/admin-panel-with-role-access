package com.project.utility.validator.imp;

import com.project.utility.validator.URL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public class URLValidator implements ConstraintValidator<URL, String> {

    private final static String pattern = "[a-z-/]*";

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {
        return object == null || object.matches(pattern);
    }
}
