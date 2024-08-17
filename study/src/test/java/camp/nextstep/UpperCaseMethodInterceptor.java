package camp.nextstep;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UpperCaseMethodInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher = new SayMethodMatcher();

    @Override
    public Object intercept(final Object o, final Method method, final Object[] objects, final MethodProxy methodProxy) throws Throwable {
        if (methodMatcher.matches(method)) {
            return methodProxy.invokeSuper(o, objects).toString().toUpperCase();
        }

        return methodProxy.invokeSuper(o, objects);
    }
}
