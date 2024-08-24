package samples;

import com.interface21.beans.factory.proxy.AfterReturningAdvice;

import java.lang.reflect.Method;

public class CounterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(final Object returnValue, final Method method, final Object[] args, final Object target) throws Throwable {
        if (returnValue.equals("success")) {
            SayCounter.count();
        }
    }
}
