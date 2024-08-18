package camp.nextstep;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher {

    @Override
    public boolean matches(final Method method) {
        return method.getName().startsWith("say");
    }
}
