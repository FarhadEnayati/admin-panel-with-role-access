package com.project.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
public class MapBuilder {

    private Map<String, Object> params;

    public MapBuilder() {
        params = new HashMap<>();
    }

    public MapBuilder(Map<String, Object> params) {
        this.params = params;
    }

    public MapBuilder addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
