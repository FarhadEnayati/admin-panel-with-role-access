package com.project.core.service;

import java.util.Collection;
import java.util.List;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public interface ICustomMapper {

    <T> T map(Object source, Class<T> destinationClass);

    <T> Collection<T> mapCollection(Collection<?> list, Class<T> viewClass);

    <T> List<T> mapList(Collection<?> list, Class<T> viewClass);

}
