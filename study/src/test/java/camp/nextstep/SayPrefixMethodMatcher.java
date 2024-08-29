package camp.nextstep;

import java.lang.reflect.Method;

public class SayPrefixMethodMatcher implements MethodMatcher {
    private static final String PREFIX_ARGUMENT = "say";

    @Override
    public boolean matches(Method m) {
        return m.getName().startsWith(PREFIX_ARGUMENT);
    }
}
