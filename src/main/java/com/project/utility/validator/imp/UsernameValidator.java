package com.project.utility.validator.imp;

import com.project.utility.validator.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    private final static String pattern = "[a-zA-Z_]*";

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {
        return object.matches(pattern);
    }
}
