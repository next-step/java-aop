package camp.nextstep.step1.cglib;

import camp.nextstep.step1.MethodMatcher;
import camp.nextstep.step1.dynamic.HelloTarget;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class HelloMethodInterceptor implements MethodInterceptor {
    private MethodMatcher methodMatcher;

    public HelloMethodInterceptor(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (methodMatcher.matches(method, HelloTarget.class, args)) {
            return ((String) proxy.invokeSuper(obj, args)).toUpperCase();
        }

        return proxy.invokeSuper(obj, args);
    }
}
