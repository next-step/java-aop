package com.interface21.beans.factory.proxy;

public class Target<T> {

    private final Class<T> type;
    private final T target;
    private final Object[] constructorParameters;

    public Target(T target, Object[] constructorParameters) {
        this.type = (Class<T>) target.getClass();
        this.target = target;
        this.constructorParameters = constructorParameters;
    }

    public Class<T> getType() {
        return type;
    }

    public T getTarget() {
        return target;
    }
}
