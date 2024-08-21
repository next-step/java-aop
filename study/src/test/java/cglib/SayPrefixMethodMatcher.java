package cglib;

import java.lang.reflect.Method;
import org.springframework.aop.MethodMatcher;

public class SayPrefixMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().startsWith("say");
    }

    @Override
    public boolean isRuntime() {
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
        return false;
    }
}
