package camp.nextstep.dynamic;

import java.lang.reflect.Method;

public class MethodMatcher {

    boolean matches(Method method) {
        return method.getName().startsWith("say");
    }
}
