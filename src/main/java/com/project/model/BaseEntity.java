package com.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.project.core.exception.ExceptionEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEntity<PK extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected PK id;

    @JsonInclude(Include.NON_NULL)
    private ExceptionEntity exception;
}
