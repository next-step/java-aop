package com.interface21.transaction;

import com.interface21.framework.AfterThrowingAdvice;
import com.interface21.framework.MethodInvocation;
import com.interface21.transaction.PlatformTransactionManager;

public class TransactionAfterThrowingAdvice implements AfterThrowingAdvice {

    private final PlatformTransactionManager platformTransactionManager;

    public TransactionAfterThrowingAdvice(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }

    @Override
    public void invoke(MethodInvocation invocation) throws Throwable {
        platformTransactionManager.rollback();
    }
}
