package com.project.utility.dozer;

import com.project.core.service.ICustomMapper;
import com.project.model.BaseEntity;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public class ViewInjectingReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    private final ICustomMapper mapper;

    public ViewInjectingReturnValueHandler(HandlerMethodReturnValueHandler delegate, ICustomMapper mapper) {
        this.delegate = delegate;
        this.mapper = mapper;
    }

    private Class getDeclaredViewClass(MethodParameter returnType) {
        ResponseView annotation = returnType.getMethodAnnotation(ResponseView.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return null;
        }
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        Class viewClass = getDeclaredViewClass(returnType);
        if (viewClass != null) {
            if (returnValue instanceof BaseEntity) {
                returnValue = wrapResult(returnValue, viewClass);
            } else if (returnValue instanceof List) {
                returnValue = wrapResultList((List) returnValue, viewClass);
            } else if (returnValue instanceof Set) {
                returnValue = wrapResultList((Set<BaseEntity>) returnValue, viewClass);
            }
        }
        delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    private <T> T wrapResult(Object result, Class<T> viewClass) {
        return mapper.map(result, viewClass);
    }

    private <T> List<T> wrapResultList(Collection<?> list, Class<T> viewClass) {
        return mapper.mapList(list, viewClass);
    }
}