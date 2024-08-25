package camp.nextstep.pointcut;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

public class SimpleDynamicPointcut extends DynamicMethodMatcherPointcut {

    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        // static check
        System.out.println("[static check] SimpleDynamicPointcut.matches() 호출");
        System.out.println("[static check] 호출한 메서드 이름 = " + method.getName());
        return "dynamicPointcut".equals(method.getName());
    }

    @Override
    public boolean matches(final Method method, final Class<?> targetClass, final Object... args) {
        // dynamic check
        System.out.println("[dynamic check] SimpleDynamicPointcut.matches() 호출");
        System.out.println("[dynamic check] 호출한 메서드 이름 = " + method.getName());
        int x = (Integer) args[0];
        return x != 100;
    }

    // 타깃 클래스가 SampleBean 클래스인 경우에만 적용
    @Override
    public ClassFilter getClassFilter() {
        return cls -> cls == SampleBean.class;
    }
}
