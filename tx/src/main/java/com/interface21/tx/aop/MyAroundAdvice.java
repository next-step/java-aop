package com.interface21.tx.aop;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advice.MethodInvocation;
import com.interface21.dao.DataAccessException;
import com.interface21.transaction.PlatformTransactionManager;

public class MyAroundAdvice implements AroundAdvice {
    private final PlatformTransactionManager transactionManager;

    public MyAroundAdvice(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        transactionManager.begin();

        Object returnValue;
        try {
            returnValue = methodInvocation.proceed();
        } catch (Exception e) {
            transactionManager.rollback();
            throw new DataAccessException(e);
        }
        transactionManager.commit();
        return returnValue;
    }
}
