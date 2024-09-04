package com.interface21.beans.factory;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advisor.Advisor;
import com.interface21.aop.advisor.Pointcut;
import org.junit.jupiter.api.Test;
import samples.Person;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {

    @Test
    void usageTest() {
        Pointcut pointcut = (method, targetClass) -> method.getName().contains("getName");
        AroundAdvice aroundAdvice = methodInvocation -> methodInvocation.proceed().toString().toUpperCase();
        Advisor advisor = new Advisor(pointcut, aroundAdvice);

        final var proxyFactoryBean = new ProxyFactoryBean<>(
                Person.class,
                Person.class,
                advisor
        );
        final var proxy = proxyFactoryBean.getObject();
        proxy.setName("abc");
        proxy.setNickname("abcdef");

        assertThat(proxyFactoryBean.getObjectType()).isEqualTo(Person.class);

        assertThat(proxy.getName()).isEqualTo("ABC");
        assertThat(proxy.getNickname()).isEqualTo("abcdef");
    }
}
