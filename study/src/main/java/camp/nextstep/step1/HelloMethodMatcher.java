package camp.nextstep.step1;

import java.lang.reflect.Method;

public class HelloMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(Method m, Class<?> targetClass, Object[] args) {
        return m.getName().startsWith("say");
    }
}
