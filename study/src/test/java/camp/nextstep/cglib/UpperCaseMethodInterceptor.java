package camp.nextstep.cglib;

import camp.nextstep.MethodMatcher;
import camp.nextstep.SayPrefixMethodMatcher;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UpperCaseMethodInterceptor implements MethodInterceptor {

        private final MethodMatcher matcher;

        public UpperCaseMethodInterceptor() {
            this.matcher = new SayPrefixMethodMatcher();
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object result = proxy.invokeSuper(obj, args);
            if (matcher.matches(method)) {
                return ((String) result).toUpperCase();
            }
            return result;
        }
}
