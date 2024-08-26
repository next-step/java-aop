package study;

import com.interface21.beans.factory.MethodMatcher;
import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {
    @Override
    public boolean matches(final Method m, final Class<?> targetClass, final Object[] args) {
        return m.getName().startsWith("say");
    }
}
