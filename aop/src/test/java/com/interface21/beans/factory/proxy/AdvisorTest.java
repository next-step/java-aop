package com.interface21.beans.factory.proxy;

import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdvisorTest {

    private final MethodProxy methodProxy = mock(MethodProxy.class);
    private final TargetObject targetObject = new TargetObject();
    private final Method tagetMethod = TargetObject.class.getMethod("say", String.class);

    AdvisorTest() throws NoSuchMethodException {
    }

    @BeforeEach
    void setUp() throws Throwable {
        when(methodProxy.invokeSuper(targetObject, new String[]{"jinyoung"})).thenReturn("jinyoung");
    }

    @Test
    void 매칭되지_않는_경우_기본_실행을_동작한다() throws Throwable {
        Advisor advisor = new Advisor(
                method -> method.getName().equals("noSay"),
                new UpperCaseAdvice()
        );

        Object actual = advisor.createMethodInterceptor()
                .intercept(targetObject, tagetMethod, new String[]{"jinyoung"}, methodProxy);
        assertThat(actual).isEqualTo("jinyoung");
    }

    @Test
    void 매칭되는_경우_advice를_통해_실행을_동작한다() throws Throwable {
        Advisor advisor = new Advisor(
                method -> method.getName().equals("say"),
                new UpperCaseAdvice()
        );

        Object actual = advisor.createMethodInterceptor()
                .intercept(targetObject, tagetMethod, new String[]{"jinyoung"}, methodProxy);
        assertThat(actual).isEqualTo("JINYOUNG");
    }

    private static class UpperCaseAdvice implements Advice {

        @Override
        public Object invoke(JoinPoint joinPoint) throws Throwable {
            return ((String) joinPoint.proceed()).toUpperCase();
        }
    }

    private static class TargetObject {

        public String say(String name) {
            return name;
        }
    }
}
