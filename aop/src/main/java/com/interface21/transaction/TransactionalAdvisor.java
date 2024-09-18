package com.interface21.transaction;

import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;

public class TransactionalAdvisor implements Advisor {

    private final Interceptor transactionalAdvice;
    private final TransactionalMatcher pointcut;

    public TransactionalAdvisor(PlatformTransactionManager platformTransactionManager) {
        this.transactionalAdvice = new TransactionalAdvice(platformTransactionManager);
        this.pointcut = new TransactionalPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Interceptor getAdvice() {
        return this.transactionalAdvice;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return transactionalAdvice.invoke(methodInvocation);
    }
}
