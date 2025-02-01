package com.demo.cloud.util;

import java.util.Iterator;

public class CycleIterator<T> implements Iterator<T> {
    private final T[] data;
    private int currentIndex = -1;

    public CycleIterator(T[] data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        currentIndex = (currentIndex + 1) % data.length;
        return data[currentIndex];
    }

    public int size() {
        return data.length;
    }
}
