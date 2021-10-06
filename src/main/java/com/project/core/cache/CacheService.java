package com.project.core.cache;

import com.project.utility.SecurityUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@Component("CacheService1")
@SuppressWarnings("unchecked")
public class CacheService implements ICacheService {

    private static CacheManager manager = null;

    @Override
    public void addCache(String cacheName, String key, Object data) {
        Cache cache = getCacheManager().getCache(cacheName);
        cache.remove(key);
        Element element = new Element(key, data);
        cache.put(element);
    }

    @Override
    public void addCache(String cacheName, Object data) {
        String key = SecurityUtil.getCurrentUser().getUserName();
        Cache cache = getCacheManager().getCache(cacheName);
        cache.remove(key);
        Element element = new Element(key, data);
        cache.put(element);
    }

    @Override
    public <U> List<U> getAllCache(String cacheName, Class<U> transformerClass) {
        Cache cache = getCacheManager().getCache(cacheName);
        Map<Object, Element> elements = cache.getAll(cache.getKeys());
        if (elements == null) {
            return null;
        }
        List<U> rets = new ArrayList<>();
        for (Object key : elements.keySet()) {
            rets.add((U) elements.get(key).getObjectValue());
        }
        return rets;
    }

    @Override
    public <U> U getCache(String cacheName, String key, Class<U> transformerClass) {
        Cache cache = getCacheManager().getCache(cacheName);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        Object data = element.getObjectValue();
        return (U) data;
    }

    @Override
    public <U> U getCache(String cacheName, Class<U> transformerClass) {
        String key = SecurityUtil.getCurrentUser().getUserName();
        Cache cache = getCacheManager().getCache(cacheName);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        Object data = element.getObjectValue();
        return (U) data;
    }

    private CacheManager getCacheManager() {
        if (manager == null) {
            manager = CacheManager.create();
        }
        return manager;
    }

    @Override
    public void removeCache(String cacheName, String key) {
        Cache cache = getCacheManager().getCache(cacheName);
        cache.remove(key);
    }

    @Override
    public void removeCache(String cacheName) {
        String key = SecurityUtil.getCurrentUser().getUserName();
        Cache cache = getCacheManager().getCache(cacheName);
        cache.remove(key);
    }

}
