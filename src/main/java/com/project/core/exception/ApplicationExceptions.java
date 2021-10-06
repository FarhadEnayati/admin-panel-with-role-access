package com.project.core.exception;

import com.project.utility.MapBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Component
public class ApplicationExceptions extends RuntimeException {

    private String customMsg;
    private ExceptionEntity exceptionEntity = null;
    private Map<String, Object> params = null;
    private String propName = "messageResources_fa.properties";
    private Integer code = null;
    private ApplicationExceptionsType typeMsg = ApplicationExceptionsType.ERROR;
    private static Properties prop = null;

    @PostConstruct
    public void init() {
        try {
            prop = new Properties();
            prop.load(ApplicationExceptions.class.getResourceAsStream("/" + propName));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ApplicationExceptions() {
        setException();
    }

    public ApplicationExceptions(String customMsg) {
        this.customMsg = customMsg;
        setException();
    }

    public ApplicationExceptions(Exception e) {
        if (e instanceof ApplicationExceptions) {
            this.exceptionEntity = ((ApplicationExceptions) e).getExceptionEntity();
        } else {
            this.customMsg = e.getMessage();
            this.exceptionEntity = new ExceptionEntity(this.customMsg, typeMsg.getValue(), this.code);
        }
    }

    public ApplicationExceptions(String customMsg, String propName, MapBuilder builder, ApplicationExceptionsType typeMsg) {
        this.customMsg = customMsg;
        this.propName = propName;
        this.params = builder != null ? builder.getParams() : null;
        this.typeMsg = typeMsg;
        setException();
    }

    public ApplicationExceptions(String customMsg, ApplicationExceptionsType typeMsg) {
        this.customMsg = customMsg;
        this.typeMsg = typeMsg;
        setException();
    }

    public ApplicationExceptions(String customMsg, Integer code, ApplicationExceptionsType typeMsg) {
        this.customMsg = customMsg;
        this.code = code;
        this.typeMsg = typeMsg;
        setException();
    }

    public ApplicationExceptions(String customMsg, MapBuilder builder) {
        this.customMsg = customMsg;
        this.params = builder != null ? builder.getParams() : null;
        setException();
    }

    public ApplicationExceptions(String customMsg, MapBuilder builder, ApplicationExceptionsType typeMsg) {
        this.customMsg = customMsg;
        this.params = builder != null ? builder.getParams() : null;
        this.typeMsg = typeMsg;
        setException();
    }

    public ApplicationExceptions(String customMsg, ApplicationExceptionsType typeMsg,
                                 MapBuilder builder) {
        this.customMsg = customMsg;
        this.typeMsg = typeMsg;
        this.params = builder != null ? builder.getParams() : null;
        setException();
    }

    private void setException() {
        if (prop == null) {
            return;
        }
        String msg = null;
        try {
            String exception = this.customMsg;
            if (!StringUtils.isEmpty(exception)) {
                String temp = prop.getProperty(exception);
                if (temp != null) {
                    msg = temp;
                } else {
                    msg = exception;
                }
            }
        } catch (Exception e) {
            msg = null;
        }
        if (StringUtils.isEmpty(msg)) {
            msg = prop.getProperty("public.exception");

        } else if (this.params != null) {
            Set<String> paramsKey = params.keySet();
            for (String key : paramsKey) {
                String value = params.get(key).toString();
                msg = msg.replace("@" + key, value);
            }
        }
        this.exceptionEntity = new ExceptionEntity(msg, typeMsg.getValue(), this.code);
    }

    public ExceptionEntity getExceptionEntity() {
        return exceptionEntity;
    }
}