package com.demo.cloud.filterParams;

import java.util.HashMap;

public abstract class FilterParams {
    private final HashMap<String, String> params = new HashMap<>();

    protected void putIfNotNull(String key, String value) {
        if (value != null) {
            params.put(key, value);
        }
    }

    public HashMap<String, String> getParams() {
        return params;
    }
}
