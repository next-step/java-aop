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
        factoryBean.addAdvice(beforeAdvice);

        final FakeClass proxy = factoryBean.getObject();
        proxy.sayHello();

        assertThat(beforeAdvice.isBeforeCalled()).isTrue();
    }

    @Test
    @DisplayName("AfterReturningAdvice 가 정상적으로 호출된다.")
    void afterReturningAdviceTest() {
        final ProxyFactoryBean<FakeClass> factoryBean = new ProxyFactoryBean<>(FakeClass.class);
        final FakeAfterReturningAdvice afterReturningAdvice = new FakeAfterReturningAdvice();
        factoryBean.addAdvice(afterReturningAdvice);

        final FakeClass proxy = factoryBean.getObject();
        proxy.sayHello();

        assertThat(afterReturningAdvice.isAfterCalled()).isTrue();
    }

    static class FakeClass {
        public String sayHello() {
            return "Hello";
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
}
