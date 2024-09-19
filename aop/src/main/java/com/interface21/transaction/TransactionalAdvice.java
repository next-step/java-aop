package com.interface21.transaction;

import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;

public class TransactionalAdvice implements Interceptor {

    private final PlatformTransactionManager transactionalManager;

    public TransactionalAdvice(PlatformTransactionManager transactionalManager) {
        this.transactionalManager = transactionalManager;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        try {
            Object returned = invocation.proceed();
            transactionalManager.commit();

            return returned;

        } catch (Throwable throwable) {
            if (throwable instanceof RuntimeException) {
                transactionalManager.rollback();
            }
            throw throwable;
        }


    }
}
