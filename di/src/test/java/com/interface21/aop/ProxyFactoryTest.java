package com.interface21.aop;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advisor.Advisor;
import com.interface21.aop.advisor.Pointcut;
import org.junit.jupiter.api.Test;
import samples.Person;
import samples.PersonImpl;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryTest {
    @Test
    void usageTest() {
        final Person target = new PersonImpl("abc", "abcdef");
        final ProxyFactory proxyFactory = new ProxyFactory(target, new Class[]{Person.class});

        final Pointcut pointcut = (method, targetClass) -> method.getName().contains("Name");
        final AroundAdvice uppercaseAdvice = joinPoint -> joinPoint.proceed().toString().toUpperCase();
        final Advisor advisor = new Advisor(pointcut, uppercaseAdvice);
        proxyFactory.addAdvisor(advisor);

        final Person proxy = (Person) proxyFactory.getProxy();

        assertThat(proxy.getName()).isEqualTo("ABC");
        assertThat(proxy.getNickname()).isEqualTo("abcdef");
    }
}
