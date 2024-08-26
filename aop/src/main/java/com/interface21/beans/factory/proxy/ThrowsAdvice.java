package com.interface21.beans.factory.proxy;

public interface ThrowsAdvice extends Advice {
    void afterThrowing(final Exception ex);
}
