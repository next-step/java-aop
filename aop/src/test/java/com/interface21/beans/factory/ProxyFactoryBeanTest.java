package com.interface21.beans.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {

    @Test
    @DisplayName("프록시 객체를 생성할 수 있다.")
    void proxyCreationTest() {
        final ProxyFactoryBean<FakeClass> factoryBean = new ProxyFactoryBean<>(FakeClass.class);

        final FakeClass proxy = factoryBean.getObject();

        assertThat(proxy).isInstanceOf(FakeClass.class);
    }

    @Test
    @DisplayName("프록시 객체가 기존 객체의 메서드를 호출할 수 있다.")
    void proxyMethodInvocationTest() {
        final ProxyFactoryBean<FakeClass> factoryBean = new ProxyFactoryBean<>(FakeClass.class);
        final FakeClass proxy = factoryBean.getObject();

        final String result = proxy.sayHello();

        assertThat(result).isEqualTo("Hello");
    }


    @Test
    @DisplayName("BeforeAdvice 가 정상적으로 호출된다.")
    void beforeAdviceTest() {
        final ProxyFactoryBean<FakeClass> factoryBean = new ProxyFactoryBean<>(FakeClass.class);
        final FakeBeforeAdvice beforeAdvice = new FakeBeforeAdvice();
        final SayHelloPointCut pointCut = new SayHelloPointCut();
        final DefaultAdvisor advisor = new DefaultAdvisor(beforeAdvice, pointCut);
        factoryBean.addAdvisors(advisor);

        final FakeClass proxy = factoryBean.getObject();
        proxy.sayHello();

        assertThat(beforeAdvice.isBeforeCalled()).isTrue();
    }

    @Test
    @DisplayName("AfterReturningAdvice 가 정상적으로 호출된다.")
    void afterReturningAdviceTest() {
        final ProxyFactoryBean<FakeClass> factoryBean = new ProxyFactoryBean<>(FakeClass.class);
        final FakeAfterReturningAdvice afterReturningAdvice = new FakeAfterReturningAdvice();
        final SayHelloPointCut pointCut = new SayHelloPointCut();
        final DefaultAdvisor advisor = new DefaultAdvisor(afterReturningAdvice, pointCut);
        factoryBean.addAdvisors(advisor);

        final FakeClass proxy = factoryBean.getObject();
        proxy.sayHello();

        assertThat(afterReturningAdvice.isAfterCalled()).isTrue();
    }

    @Test
    @DisplayName("적절한 PointCut 이 없는 경우 Advice 가 호출되지 않는다.")
    void pointCutMismatchTest() {
        final ProxyFactoryBean<FakeClass> factoryBean = new ProxyFactoryBean<>(FakeClass.class);
        final FakeBeforeAdvice beforeAdvice = new FakeBeforeAdvice();
        final SayHelloPointCut pointCut = new SayHelloPointCut();
        final DefaultAdvisor advisor = new DefaultAdvisor(beforeAdvice, pointCut);
        factoryBean.addAdvisors(advisor);

        final FakeClass proxy = factoryBean.getObject();
        proxy.sayBye();

        assertThat(beforeAdvice.isBeforeCalled()).isFalse();
    }


    @Test
    @DisplayName("ThrowsAdvice 가 정상적으로 호출된다.")
    void throwsAdviceTest() {
        final ProxyFactoryBean<FakeClass> factoryBean = new ProxyFactoryBean<>(FakeClass.class);
        final FakeThrowsAdvice throwsAdvice = new FakeThrowsAdvice();
        final ExceptionPointCut pointCut = new ExceptionPointCut();
        final DefaultAdvisor advisor = new DefaultAdvisor(throwsAdvice, pointCut);
        factoryBean.addAdvisors(advisor);

        final FakeClass proxy = factoryBean.getObject();

        try {
            proxy.exception();
        } catch (final Exception ignored) {

        }

        assertThat(throwsAdvice.isAfterCalled()).isTrue();
    }

    static class FakeClass {
        public String sayHello() {
            return "Hello";
        }

        public String sayBye() {
            return "Bye";
        }

        public String exception() {
            throw new RuntimeException();
        }
    }

    static class FakeBeforeAdvice implements MethodBeforeAdvice {
        private boolean beforeCalled = false;

        @Override
        public void before(final Method method, final Object[] args, final Object target) {
            beforeCalled = true;
        }

        public boolean isBeforeCalled() {
            return beforeCalled;
        }
    }

    static class FakeAfterReturningAdvice implements AfterReturningAdvice {
        private boolean afterCalled = false;

        @Override
        public void afterReturning(final Object returnValue, final Method method, final Object[] args, final Object target) {
            afterCalled = true;
        }

        public boolean isAfterCalled() {
            return afterCalled;
        }
    }

    static class FakeThrowsAdvice implements ThrowsAdvice {
        private boolean afterCalled = false;

        @Override
        public void afterThrowing(final Exception ex, final Method method, final Object[] args, final Object target) {
            afterCalled = true;
        }

        public boolean isAfterCalled() {
            return afterCalled;
        }
    }

    static class SayHelloPointCut implements PointCut {
        @Override
        public MethodMatcher getMethodMatcher() {
            return (method, targetClass, args) -> method.getName().equals("sayHello");
        }
    }

    static class ExceptionPointCut implements PointCut {
        @Override
        public MethodMatcher getMethodMatcher() {
            return (method, targetClass, args) -> method.getName().equals("exception");
        }
    }
}
