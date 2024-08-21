package dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class UpperCaseInvocationHandler implements InvocationHandler {

    private Object target;
    private final Map<String, Method> methods = new HashMap<>();

    public UpperCaseInvocationHandler(Object target) {
        this.target = target;
        for (Method method : target.getClass().getMethods()) {
            if (method.getName().startsWith("say")) {
                methods.put(method.getName(), method);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method targetMethod = methods.get(method.getName());
        if (targetMethod != null) {
            String result = (String) targetMethod.invoke(target, args);
            return result.toUpperCase();
        }
        return method.invoke(target, args);
    }
}
