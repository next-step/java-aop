package study.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseInvocationHandler implements InvocationHandler {
    private final Object target;

    public UppercaseInvocationHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        String result = (String) method.invoke(target, args);
        return result.toUpperCase();
    }
}
