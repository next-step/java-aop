package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.AfterReturningAdvice;
import com.interface21.beans.factory.MethodBeforeAdvice;
import com.interface21.beans.factory.ThrowsAdvice;
import com.interface21.transaction.PlatformTransactionManager;

import java.lang.reflect.Method;

public class TransactionAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    private final PlatformTransactionManager transactionManager;

    public TransactionAdvice(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void before(final Method method, final Object[] args, final Object target) {
        transactionManager.begin();
    }

    @Override
    public void afterReturning(final Object returnValue, final Method method, final Object[] args, final Object target) {
        transactionManager.commit();
    }

    @Override
    public void afterThrowing(final Exception ex, final Method method, final Object[] args, final Object target) {
        transactionManager.rollback();
    }
}
