package com.interface21.beans.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    static class FakeClass {
        public String sayHello() {
            return "Hello";
        }
    }
}
