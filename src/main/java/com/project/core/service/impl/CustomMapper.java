package com.project.core.service.impl;

import com.project.core.exception.ApplicationExceptions;
import com.project.core.service.ICustomMapper;
import com.project.utility.dozer.ViewInjectingArgumentValueHandler;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Service
public class CustomMapper implements ICustomMapper {

    @Autowired
    private Mapper mapper;

    @Autowired
    private Validator validator;

    private boolean validate = false;

    @Override
    public <T> Collection<T> mapCollection(Collection<?> list, Class<T> viewClass) {
        Set<T> retValues = new HashSet<>();
        setValidate();
        for (Object entity : list) {
            retValues.add(onlyMap(entity, viewClass));
        }
        return retValues;
    }

    @Override
    public <T> List<T> mapList(Collection<?> list, Class<T> viewClass) {
        List<T> retValues = new LinkedList<>();
        setValidate();
        for (Object entity : list) {
            retValues.add(onlyMap(entity, viewClass));
        }
        return retValues;
    }

    @Override
    public <T> T map(Object source, Class<T> destinationClass) {
        setValidate();
        return onlyMap(source, destinationClass);
    }

    private <T> T onlyMap(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        if (validate) {
            Set<ConstraintViolation<Object>> res = validator.validate(source);
            if (res.size() != 0) {
                ConstraintViolation<Object> violation = res.iterator().next();
                throw new ApplicationExceptions(violation.getMessage());
            }
        }
        return mapper.map(source, destinationClass);
    }

    private void setValidate() {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTraces[3];
        validate = caller.getClassName().equals(ViewInjectingArgumentValueHandler.class.getName());
    }

}
