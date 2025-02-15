package com.demo.cloud.filterParams;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;

public abstract class FilterParams {
    private final HashMap<String, String> params = new HashMap<>();

    protected void putIfNotNull(Object... values) {
        Class<? extends FilterParams> clazz = this.getClass();
        Constructor<?>[] cs = clazz.getDeclaredConstructors();
        if (cs.length != 1) {
            throw new IllegalArgumentException(clazz.getSimpleName() + " has more than one constructor");
        }

        putIfNotNull(cs[0], values);
    }

    protected void putIfNotNull(Constructor<?> c, Object[] values) {
        Parameter[] params = c.getParameters();
        putIfNotNull(params, values);
    }

    protected void putIfNotNull(Parameter[] params, Object[] values) {
        if (params.length != values.length) {
            throw new IllegalArgumentException("Params and values length do not match");
        }

        for (int i = 0; i < params.length; i++) {
            putIfNotNull(params[i].getName(), values[i]);
        }
    }

    protected void putIfNotNull(String key, String value) {
        if (value != null) {
            params.put(key, value);
        }
    }

    protected void putIfNotNull(String key, Object value) {
        if (value != null) {
            params.put(key, value.toString());
        }
    }

    public HashMap<String, String> getParams() {
        return params;
    }
}
