package com.interface21.beans.config;

import com.interface21.beans.factory.proxy.MethodBeforeAdvice;
import com.interface21.transaction.PlatformTransactionManager;

import java.lang.reflect.Method;

public class TransactionBeginAdvice implements MethodBeforeAdvice {

    private final PlatformTransactionManager transactionManager;

    public TransactionBeginAdvice(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void invoke(final Method method, final Object[] args, final Object target) throws Throwable {
        transactionManager.begin();
    }
}
