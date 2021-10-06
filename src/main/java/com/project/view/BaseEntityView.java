package com.project.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.project.core.exception.ExceptionEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Data
public abstract class BaseEntityView<PK extends Serializable> implements Serializable {

    @JsonInclude(Include.NON_NULL)
    private PK id;

    @JsonInclude(Include.NON_NULL)
    private ExceptionEntity exception;

}
