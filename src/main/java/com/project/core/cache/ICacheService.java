package com.project.core.cache;

import java.util.List;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public interface ICacheService {

    void addCache(String cacheName, String key, Object data);

    void addCache(String cacheName, Object data);

    <U> List<U> getAllCache(String cacheName, Class<U> transformerClass);

    <U> U getCache(String cacheName, String key, Class<U> transformerClass);

    <U> U getCache(String cacheName, Class<U> transformerClass);

    void removeCache(String cacheName, String key);

    void removeCache(String cacheName);


}
