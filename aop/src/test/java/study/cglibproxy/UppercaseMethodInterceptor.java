package study.cglibproxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import com.interface21.beans.factory.MethodMatcher;

public class UppercaseMethodInterceptor implements MethodInterceptor {
    private final MethodMatcher methodMatcher;

    public UppercaseMethodInterceptor(final MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    /**
     * 주의사항
     * proxy.invoke() 혹은 method.invoke()를 사용할 경우 StackOverFlowError가 발생할 수 있다.
     * - 프록시 객체의 메서드 호출 → intercept() 호출 → 또 다시 프록시 객체의 메서드 호출 → ... → StackOverFlowError
     */
    @Override
    public Object intercept(final Object obj, final Method method, final Object[] args, final MethodProxy proxy) throws Throwable {
        String result = (String) proxy.invokeSuper(obj, args);
        if (methodMatcher.matches(method, obj.getClass(), args)) {
            return result.toUpperCase();
        }

        return result;
    }
}
