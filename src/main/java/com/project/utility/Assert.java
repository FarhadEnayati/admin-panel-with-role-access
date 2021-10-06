package com.project.utility;

import com.project.core.exception.ApplicationExceptions;
import com.project.core.exception.ApplicationExceptionsType;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public abstract class Assert {

    private static final String EMPTY_STRING = "";

    public static <T extends Object> boolean isEmpty(List<T> arg) {

        return arg == null || arg.isEmpty();
    }

    public static boolean isEmpty(Map<String, Object> arg) {

        return arg == null || arg.isEmpty();
    }

    public static <T extends Object> boolean isEmpty(Set<T> arg) {

        return arg == null || arg.isEmpty();
    }

    public static boolean isEmpty(String arg) {

        return StringUtils.isEmpty(arg);
    }

    public static <T extends Object> boolean isEmpty(T[] arg) {

        return arg == null || arg.length == 0;
    }

    public static boolean isInList(Integer arg, List<Integer> target) {

        List<Integer> temp = new ArrayList<>(target);
        temp.removeAll(Collections.singleton(null));
        return temp.contains(arg);
    }

    public static boolean isInList(String arg, List<String> target) {

        List<String> temp = new ArrayList<>(target);
        temp.removeAll(Collections.singleton(null));
        return temp.contains(arg);
    }

    public static boolean isNull(Object... objects) {

        for (Object arg : objects) {
            if (arg != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Object object) {

        return object == null;
    }

    public static <T extends Object> void NOT_NULL(List<T> arg) {

        if (arg == null || arg.isEmpty()) {
            exp();
        }
    }

    public static <T extends Object> void NOT_NULL(Object arg) {

        if (arg == null) {
            exp();
        }
    }

    public static <T extends Object> void NOT_NULL(List<T> arg, String message) {
        if (arg == null || arg.isEmpty()) {
            throw new ApplicationExceptions(message);
        }
    }

    public static <T extends Object> void NOT_NULL(List<T> arg, String message, Map<String, Object> params) {
        if (arg == null || arg.isEmpty()) {
            throw new ApplicationExceptions(message, new MapBuilder(params));
        }
    }

    public static <T extends Object> void NOT_NULL(List<T> arg, String message, Map<String, Object> params,
                                                   String titleMsg) {
        if (arg == null || arg.isEmpty()) {
            throw new ApplicationExceptions(message, new MapBuilder(params), ApplicationExceptionsType.ERROR);
        }
    }

    public static <T extends Object> void NOT_NULL(Set<T> arg) {
        if (arg == null || arg.isEmpty()) {
            exp();
        }
    }

    public static void NOT_NULL(String arg) {
        if (StringUtils.isEmpty(arg)) {
            exp();
        }
    }

    public static void NOT_NULL(String arg, String message) {
        if (StringUtils.isEmpty(arg)) {
            throw new ApplicationExceptions(message, ApplicationExceptionsType.ERROR);
        }
    }

    public static void MAX_LENGTH(String field, Integer max) {
        if (field != null && field.length() > max) {
            exp();
        }
    }

    public static void MAX_LENGTH(String field, Integer max, String message) {
        if (field != null && field.length() > max) {
            exp(message);
        }
    }

    public static void MAX_LENGTH(String field, Integer max, String message, Map<String, Object> params) {
        if (field != null && field.length() > max) {
            exp(message, new MapBuilder(params));
        }
    }

    public static void TRUE(boolean expression) {
        if (!expression) {
            exp();
        }
    }

    public static void TRUE(boolean expression, String message) {
        if (!expression) {
            throw new ApplicationExceptions(message);
        }
    }

    public static void TRUE(boolean expression, String message, Map<String, Object> params) {
        if (!expression) {
            throw new ApplicationExceptions(message, new MapBuilder(params));
        }
    }

    public static void TRUE(boolean expression, String message, Map<String, Object> params,
                            ApplicationExceptionsType typeMsg) {
        if (!expression) {
            throw new ApplicationExceptions(message, new MapBuilder(params), typeMsg);
        }
    }

    public static void TRUE(boolean expression, String message, MapBuilder builder) {
        if (!expression) {
            throw new ApplicationExceptions(message, builder);
        }
    }

    public static void TRUE(boolean expression, String message, MapBuilder builder,
                            ApplicationExceptionsType typeMsg) {
        if (!expression) {
            throw new ApplicationExceptions(message, builder, typeMsg);
        }
    }

    public static void TRUE(boolean expression, String message, ApplicationExceptionsType typeMsg) {
        if (!expression) {
            throw new ApplicationExceptions(message, typeMsg);
        }
    }

    private static void exp() throws ApplicationExceptions {
        exp(EMPTY_STRING, null, ApplicationExceptionsType.ERROR);
    }

    private static void exp(Exception exp) {
        throw new ApplicationExceptions(exp);
    }

    private static void exp(String message) {
        throw new ApplicationExceptions(message, (MapBuilder) null, ApplicationExceptionsType.ERROR);
    }

    private static void exp(String message, ApplicationExceptionsType exceptionsType) {
        throw new ApplicationExceptions(message, new MapBuilder(), exceptionsType);
    }

    private static void exp(String message, MapBuilder params) {
        throw new ApplicationExceptions(message, params, ApplicationExceptionsType.ERROR);
    }

    private static void exp(String message, MapBuilder params, ApplicationExceptionsType exceptionsType) {
        throw new ApplicationExceptions(message, params, exceptionsType);
    }

}
