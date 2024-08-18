package camp.nextstep;

import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Method m);
}
