package com.interface21.beans.factory;

public interface FactoryBean<T> {
    T getObject();

    Class<T> getType();
}
