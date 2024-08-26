package com.interface21.transaction;

import com.interface21.beans.factory.MethodMatcher;
import com.interface21.dao.DataAccessException;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

//todo 빈으로 등록하고 싶음
public class TransactionMethodInterceptor implements MethodInterceptor {
    private final PlatformTransactionManager transactionManager;  //todo 빈으로 등록된 애를 주입받고 싶음
    private final MethodMatcher methodMatcher;

    public TransactionMethodInterceptor(final PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.methodMatcher = new TransactionMethodMatcher();  //todo 얘도 빈으로 등록할 수 있지 않을까? 싶긴 한데, 그럴 필요가 있나? 싶기도..
    }

    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        if (methodMatcher.matches(method, obj.getClass(), args)) {
            transactionManager.begin();

            try {
                Object result = proxy.invokeSuper(obj, args);
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
