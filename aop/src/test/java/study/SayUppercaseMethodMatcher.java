package study;

import java.lang.reflect.Method;

public class SayUppercaseMethodMatcher implements MethodMatcher {

    public static final String SAY = "say";

    @Override
    public boolean matches(final Method m, final Class<?> targetClass, final Object[] args) {
        return m.getName().startsWith(SAY);
    }
}
