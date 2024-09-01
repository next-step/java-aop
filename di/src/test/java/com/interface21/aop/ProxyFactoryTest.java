package com.interface21.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.Test;
import samples.Person;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryTest {
    private final MethodInterceptor methodInterceptor = (obj, method, args, proxy) -> {
        Object invokeResult = proxy.invokeSuper(obj, args);

        if (method.getName().equals("getName")) {
            return invokeResult.toString().toUpperCase();
        }
        return invokeResult;
    };

    @Test
    void usageTest() {
        ProxyFactory proxyFactory = new ProxyFactory(Person.class, methodInterceptor);
        final var proxy = (Person) proxyFactory.getProxy();
        proxy.setName("abc");
        proxy.setNickname("abcdef");
        assertThat(proxy.getName()).isEqualTo("ABC");
        assertThat(proxy.getNickname()).isEqualTo("abcdef");
    }
}
