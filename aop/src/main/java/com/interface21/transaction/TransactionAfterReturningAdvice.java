package com.interface21.transaction;

import com.interface21.framework.AfterReturningAdvice;
import com.interface21.framework.MethodInvocation;

public class TransactionAfterReturningAdvice implements AfterReturningAdvice {

    PlatformTransactionManager platformTransactionManager;

    public TransactionAfterReturningAdvice(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public void invoke(MethodInvocation invocation) throws Throwable {
        platformTransactionManager.commit();
    }
}
