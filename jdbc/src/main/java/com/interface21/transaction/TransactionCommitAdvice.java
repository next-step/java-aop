package com.interface21.transaction;

import com.interface21.beans.factory.proxy.AfterReturningAdvice;

import java.lang.reflect.Method;

public class TransactionCommitAdvice implements AfterReturningAdvice {

    private final PlatformTransactionManager transactionManager;

    public TransactionCommitAdvice(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void afterReturning(final Object returnValue, final Method method, final Object[] args, final Object target) throws Throwable {
        transactionManager.commit();
    }
}
