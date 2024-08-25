package camp.nextstep.pointcut;

import camp.nextstep.sample.World;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

public class SimpleStaticPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(final Method method, final Class<?> targetClass) {
        System.out.println("SimpleStaticPointcut.matches() 호출");
        System.out.println("호출한 메서드 이름 = " + method.getName());
        return "getMessage".equals(method.getName());
    }

    // 타깃 클래스가 World 클래스인 경우에만 적용
    @Override
    public ClassFilter getClassFilter() {
        return clazz -> clazz == World.class;
    }
}
