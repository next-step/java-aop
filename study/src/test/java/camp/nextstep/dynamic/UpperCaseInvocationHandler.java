package camp.nextstep.dynamic;

import camp.nextstep.MethodMatcher;
import camp.nextstep.SayPrefixMethodMatcher;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpperCaseInvocationHandler implements InvocationHandler {

    private Hello hello;
    private final MethodMatcher matcher;

    public UpperCaseInvocationHandler(Hello hello) {
        this.hello = hello;
        this.matcher = new SayPrefixMethodMatcher();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String result = (String) method.invoke(hello, args);
        if (matcher.matches(method)){
            return result.toUpperCase();
        }
        return result;
    }
}
