package com.interface21.beans.factory.proxy.factory;

public interface FactoryBean<T> {

    T getObject() throws Exception;
}
