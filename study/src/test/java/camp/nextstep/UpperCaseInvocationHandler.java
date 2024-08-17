package camp.nextstep;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpperCaseInvocationHandler implements InvocationHandler {

    private final Object target;
    private final MethodMatcher methodMatcher = new SayMethodMatcher();

    public UpperCaseInvocationHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (methodMatcher.matches(method)) {
            return method.invoke(target, args).toString().toUpperCase();
        }

        return method.invoke(target, args);
    }
}
