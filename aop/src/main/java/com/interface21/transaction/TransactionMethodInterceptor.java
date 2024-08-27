package com.interface21.transaction;

import com.interface21.dao.DataAccessException;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class TransactionMethodInterceptor implements MethodInterceptor {
    private final PlatformTransactionManager transactionManager;
    private final Object target;
    private final TransactionMethodMatcher transactionMethodMatcher;

    public TransactionMethodInterceptor(final PlatformTransactionManager transactionManager, final Object target) {
        this.transactionManager = transactionManager;
        this.target = target;
        this.transactionMethodMatcher = new TransactionMethodMatcher();
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        if (transactionMethodMatcher.matches(method, obj.getClass(), args)) {
            transactionManager.begin();

            try {
                Object result = method.invoke(target, args);
                transactionManager.commit();
                return result;
            } catch (Exception e) {
                transactionManager.rollback();
                throw new DataAccessException(e);
            }
        }

        return proxy.invokeSuper(obj, args);
    }
}
