package com.interface21.beans.factory.proxy;

public class Target<T> {

    private final Class<T> type;

    public Target(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }
}
