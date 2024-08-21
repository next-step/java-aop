package dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import matcher.MethodMatcher;
import matcher.SayPrefixMethodMatcher;

public class UpperCaseInvocationHandler implements InvocationHandler {

    private Object target;
    private MethodMatcher methodMatcher;

    public UpperCaseInvocationHandler(Object target) {
        this.target = target;
        this.methodMatcher = new SayPrefixMethodMatcher();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodMatcher.matches(method, target.getClass(), args)) {
            String result = (String) method.invoke(target, args);
            return result.toUpperCase();
        }
        return method.invoke(target, args);
    }
}
