package com.interface21.beans.factory;

public interface FactoryBean<T> {
    T getObject() throws Exception;

    T getObject(Class<?>[] argumentTypes, Object[] arguments) throws Exception;

    Class<T> getType();
}
