package camp.nextstep.step1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UpperCaseInvocationHandler implements InvocationHandler {

    private final Hello target;

    public UpperCaseInvocationHandler(Hello target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = (String) method.invoke(target, args);
        return result.toUpperCase();
    }
}
