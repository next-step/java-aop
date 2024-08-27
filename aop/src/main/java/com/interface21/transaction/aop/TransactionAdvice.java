package com.interface21.transaction.aop;

import com.interface21.beans.factory.Advice;
import com.interface21.beans.factory.JointPoint;
import com.interface21.transaction.PlatformTransactionManager;

public class TransactionAdvice implements Advice {

    private final PlatformTransactionManager transactionManager;

    public TransactionAdvice(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Object around(JointPoint jointPoint) {
        try {
            transactionManager.begin();
            Object result = jointPoint.proceed();
            transactionManager.commit();
            return result;
        } catch (Throwable throwable) {
            transactionManager.rollback();
            throw new RuntimeException(throwable);
        }
    }
}
