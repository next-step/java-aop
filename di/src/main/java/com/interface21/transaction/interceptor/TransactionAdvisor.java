package com.interface21.transaction.interceptor;

import com.interface21.beans.factory.Advice;
import com.interface21.beans.factory.Advisor;
import com.interface21.beans.factory.PointCut;
import com.interface21.transaction.PlatformTransactionManager;

public class TransactionAdvisor implements Advisor {

    private final Advice advice;
    private final PointCut pointCut;

    public TransactionAdvisor(final PlatformTransactionManager transactionManager) {
        this.advice = new TransactionAdvice(transactionManager);
        this.pointCut = new TransactionPointcut();
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public PointCut getPointCut() {
        return pointCut;
    }

}
