package com.interface21.beans.factory.proxy;

import java.lang.reflect.Constructor;

public class Target<T> {

    private final Class<T> type;
    private final T target;
    private final Class<?>[] constructorParameterTypes;

    public Target(T target, Constructor<?> constructor) {
        this.type = (Class<T>) target.getClass();
        this.target = target;
        this.constructorParameterTypes = constructor.getParameterTypes();
    }

    public Class<T> getType() {
        return type;
    }

    public T getTarget() {
        return target;
    }

    public Class<?>[] getConstructorParameterTypes() {
        return constructorParameterTypes;
    }
}
