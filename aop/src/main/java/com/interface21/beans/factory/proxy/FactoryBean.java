package com.interface21.beans.factory.proxy;

public interface FactoryBean<T> {

    T getObject() throws Exception;
}
