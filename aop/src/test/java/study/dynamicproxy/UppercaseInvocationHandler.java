package study.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import study.SayMethodMatcher;

public class UppercaseInvocationHandler implements InvocationHandler {
    private final Object target;
    private final SayMethodMatcher sayMethodMatcher;

    public UppercaseInvocationHandler(final Object target, final SayMethodMatcher sayMethodMatcher) {
        this.target = target;
        this.sayMethodMatcher = sayMethodMatcher;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        String result = (String) method.invoke(target, args);
        if (sayMethodMatcher.matches(method, proxy.getClass(), args)) {
            return result.toUpperCase();
        }

        return result;
    }
}
