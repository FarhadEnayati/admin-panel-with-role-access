package com.project.utility.dozer;

import com.project.core.service.ICustomMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Collection;
import java.util.List;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public class ViewInjectingArgumentValueHandler implements HandlerMethodArgumentResolver {

    private final ICustomMapper mapper;

    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private CustomRequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor = null;

    public ViewInjectingArgumentValueHandler(ICustomMapper Mapper,
                                             RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.mapper = Mapper;
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    private Class getDeclaredViewClass(MethodParameter returnType) {
        RequestView annotation = returnType.getMethodAnnotation(RequestView.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return null;
        }
    }

    private CustomRequestResponseBodyMethodProcessor getRequestResponseBodyMethodProcessor() {

        if (requestResponseBodyMethodProcessor == null) {
            List<HttpMessageConverter<?>> messageConverters = requestMappingHandlerAdapter.getMessageConverters();
            requestResponseBodyMethodProcessor = new CustomRequestResponseBodyMethodProcessor(messageConverters);
        }
        return requestResponseBodyMethodProcessor;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Class viewClass = getDeclaredViewClass(parameter);
        Object retValue = null;
        if (viewClass != null) {
            retValue = getRequestResponseBodyMethodProcessor().resolveArgument(parameter, mavContainer, webRequest,
                    binderFactory, viewClass);
            retValue = wrapResult(retValue, parameter.getParameterType());
        } else {
            retValue = getRequestResponseBodyMethodProcessor().resolveArgument(parameter, mavContainer, webRequest,
                    binderFactory);
        }
        return retValue;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getRequestResponseBodyMethodProcessor().supportsParameter(parameter);
    }

    private <T> T wrapResult(Object result, Class<T> viewClass) {
        return mapper.map(result, viewClass);
    }

    private <T> List<T> wrapResultList(Collection<?> list, Class<T> viewClass) {
        return (List<T>) mapper.mapCollection(list, viewClass);
    }

}
