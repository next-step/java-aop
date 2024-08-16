package camp.nextstep.step1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpperCaseInvocationHandler implements InvocationHandler {

    private final Hello target;
    private final MethodMatcher methodMatcher;

    public UpperCaseInvocationHandler(Hello target) {
        this.target = target;
        this.methodMatcher = new SayMethodMatcher();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        if (methodMatcher.matches(method)) {
            return ((String) result).toUpperCase();
        }
        return result;
    }
}
