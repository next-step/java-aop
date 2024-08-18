package camp.nextstep;

import java.lang.reflect.Method;

public class SayPrefixMethodMatcher implements MethodMatcher {

    private static final String SAY_PREFIX = "say";
    
    @Override
    public boolean matches(Method method) {
        return method.getName().startsWith(SAY_PREFIX);
    }
}
