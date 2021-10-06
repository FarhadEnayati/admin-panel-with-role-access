package com.project.utility.dozer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseView {
    @SuppressWarnings("rawtypes")
    public Class value();
}
