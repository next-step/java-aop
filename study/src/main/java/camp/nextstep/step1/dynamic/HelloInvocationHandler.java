package camp.nextstep.step1.dynamic;

import camp.nextstep.step1.MethodMatcher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HelloInvocationHandler implements InvocationHandler {
    private Object target;
    private MethodMatcher methodMatcher;
    private final Map<String, Method> methodByName = new HashMap<>();

    public HelloInvocationHandler(Object target, MethodMatcher methodMatcher) {
        this.target = target;
        for (Method method : target.getClass().getDeclaredMethods()) {
            this.methodByName.put(method.getName(), method);
        }
        this.methodMatcher = methodMatcher;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = (String) methodByName.get(method.getName()).invoke(target, args);
        return methodMatcher.matches(method, Hello.class, args) ? result.toUpperCase() : result;
    }
}
