package camp.nextstep.step1;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UpperCaseMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(object, args);
        if (result instanceof String) {
            return ((String) result).toUpperCase();
        }
        throw new IllegalStateException();
    }
}
