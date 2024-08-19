package com.interface21.beans.factory.proxy;

@FunctionalInterface
public interface AdviceRunnable {
    void accept() throws Throwable;
}
