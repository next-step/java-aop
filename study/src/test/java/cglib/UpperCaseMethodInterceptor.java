package cglib;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.aop.MethodMatcher;

public class UpperCaseMethodInterceptor implements MethodInterceptor {

    private final MethodMatcher methodMatcher;

    public UpperCaseMethodInterceptor() {
        this.methodMatcher = new SayPrefixMethodMatcher();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String result = (String) methodProxy.invokeSuper(o, objects);
        if (methodMatcher.matches(method, o.getClass())) {
            return result.toUpperCase();
        }
        return result;
    }
}
