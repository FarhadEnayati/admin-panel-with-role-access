package com.project.controller;

import com.project.core.exception.ApplicationExceptions;
import com.project.core.exception.ApplicationExceptionsType;
import com.project.core.exception.ExceptionEntity;
import com.project.model.BaseEntity;
import com.project.utility.MapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@PreAuthorize("isAuthenticated()")
public class BaseController<T extends BaseEntity<PK>, PK extends Serializable> {

    @Autowired
    private HttpServletResponse response;

    protected void setException(String message) {
        this.setException(message, null, null, ApplicationExceptionsType.SUCCESS);
    }

    protected void setException(String message, MapBuilder builder) {
        this.setException(message, null, builder, ApplicationExceptionsType.SUCCESS);
    }

    protected void setException(String message, T entity) {
        this.setException(message, entity, null, ApplicationExceptionsType.SUCCESS);
    }

    protected void setException(String message, T entity, MapBuilder builder) {
        this.setException(message, entity, builder, ApplicationExceptionsType.SUCCESS);
    }

    protected void setException(String message, ApplicationExceptionsType exceptionsType) {
        this.setException(message, null, null, exceptionsType);
    }

    protected void setException(String message, Integer code, ApplicationExceptionsType exceptionsType) {
        this.setException(message, null, null, exceptionsType);
    }

    protected void setException(String message, MapBuilder builder, ApplicationExceptionsType exceptionsType) {
        this.setException(message, null, builder, exceptionsType);
    }

    protected void setException(String message, T entity, MapBuilder builder, ApplicationExceptionsType exceptionsType) {
        try {
            response.setStatus(HttpStatus.ACCEPTED.value());
            ExceptionEntity exceptionEntity = new ApplicationExceptions(message, builder, exceptionsType).getExceptionEntity();
            if (entity != null) {
                entity.setException(exceptionEntity);
            } else {
                response.getWriter().write(exceptionEntity.toString());
            }
        } catch (Exception e) {
        }
    }

}
