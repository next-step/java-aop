package camp.nextstep.step1;

import java.lang.reflect.Method;

public class SayMethodMatcher implements MethodMatcher{

    private static final String PREFIX_METHOD_NAME = "say";

    @Override
    public boolean matches(Method m) {
        return m.getName().startsWith(PREFIX_METHOD_NAME);
    }
}
