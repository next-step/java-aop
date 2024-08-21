package com.interface21.transaction.aop;

import com.interface21.beans.factory.proxy.Advice;
import com.interface21.beans.factory.proxy.JoinPoint;
import com.interface21.dao.DataAccessException;
import com.interface21.transaction.PlatformTransactionManager;

public class TransactionAdvice implements Advice {

    private final PlatformTransactionManager transactionManager;

    public TransactionAdvice(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(JoinPoint joinPoint) throws Throwable {
        transactionManager.begin();

        try {
            Object result = joinPoint.proceed();
            transactionManager.commit();

            return result;
        } catch (Exception e) {
            transactionManager.rollback();
            throw new DataAccessException(e);
        }
    }
}
