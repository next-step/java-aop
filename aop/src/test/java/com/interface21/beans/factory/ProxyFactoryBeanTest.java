package com.interface21.beans.factory;

import static org.assertj.core.api.Assertions.assertThat;

import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProxyFactoryBeanTest {
    @DisplayName("ProxyFactoryBean의 getObject()는 넘겨받은 타입에 대해 cglib 프록시 객체를 생성한다.")
    @Test
    void getObject() {
        MethodInterceptor methodInterceptor = (obj, method, args, proxy) -> {
            String result = (String) proxy.invokeSuper(obj, args);
            return "Hello, %s".formatted(result);
        };
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(ProxyTarget.class);
        proxyFactoryBean.addMethodInterceptor(methodInterceptor);

        ProxyTarget proxyTarget = (ProxyTarget) proxyFactoryBean.getObject();

        assertThat(proxyTarget).isInstanceOf(Factory.class);
        assertThat(proxyTarget.getName()).isEqualTo("Hello, proxy");
    }
}