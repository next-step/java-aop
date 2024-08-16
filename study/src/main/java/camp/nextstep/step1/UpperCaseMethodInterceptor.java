package camp.nextstep.step1;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UpperCaseMethodInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    public UpperCaseMethodInterceptor() {
        this.methodMatcher = new SayMethodMatcher();
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(object, args);
        if (methodMatcher.matches(method)) {
            return ((String) result).toUpperCase();
        }
        return result;
    }
}
