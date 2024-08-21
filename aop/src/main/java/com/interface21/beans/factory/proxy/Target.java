package com.interface21.beans.factory.proxy;

public class Target<T> {

    private final Class<T> type;
    public final T target;

    public Target(T target) {
        this.type = (Class<T>) target.getClass();
        this.target = target;
    }

    public Class<T> getType() {
        return type;
    }

    public T getTarget() {
        return target;
    }
}
