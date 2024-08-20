package camp.nextstep.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpperCaseMethodHandler implements InvocationHandler {

    private final MethodMatcher matcher = new MethodMatcher();
    private final Object target;

    public UpperCaseMethodHandler(final Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (matcher.matches(method)) {
            String value = method.invoke(target, args).toString();

            return value.toUpperCase();
        }

        return method.invoke(target, args);
    }
}
