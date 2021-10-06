package com.project.utility.dozer;

import com.project.core.service.ICustomMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Component
public class JsonViewSupportFactoryBean implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Autowired
    private ICustomMapper mapper;

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
        handlers.addAll(adapter.getReturnValueHandlers());

        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.addAll(adapter.getArgumentResolvers());

        decorateHandlers(handlers, argumentResolvers);
        adapter.setReturnValueHandlers(handlers);
        adapter.setArgumentResolvers(argumentResolvers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers,
                                  List<HandlerMethodArgumentResolver> argumentResolvers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                ViewInjectingReturnValueHandler decorator = new ViewInjectingReturnValueHandler(handler, mapper);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
                // log.info("JsonView decorator support wired up");
                break;
            }
        }
        for (HandlerMethodArgumentResolver argument : argumentResolvers) {
            if (argument instanceof RequestResponseBodyMethodProcessor) {
                ViewInjectingArgumentValueHandler decorator = new ViewInjectingArgumentValueHandler(mapper, adapter);
                int index = argumentResolvers.indexOf(argument);
                argumentResolvers.set(index, decorator);
                // log.info("JsonView decorator support wired up");
                break;
            }
        }
    }

}