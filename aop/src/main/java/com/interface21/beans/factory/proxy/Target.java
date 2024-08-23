package com.interface21.beans.factory.proxy;

import java.lang.reflect.Constructor;

public class Target<T> {

    private final T target;
    private final Class<?>[] constructorParameterTypes;

    public Target(T target, Constructor<?> constructor) {
        this.target = target;
        this.constructorParameterTypes = constructor.getParameterTypes();
    }

    public Class<T> getType() {
        return (Class<T>) target.getClass();
    }

    public T getTarget() {
        return target;
    }

    public Class<?>[] getConstructorParameterTypes() {
        return constructorParameterTypes;
    }
}
