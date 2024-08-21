package com.interface21.beans.factory.proxy;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProxyFactoryBeanTest {

    @Test
    void 프록시빈을_생성한다() {
        Target<Hello> target = new Target<>(new Hello(), new Object[0]);
        Advisor advisor = new Advisor(
                method -> method.getName().startsWith("say"),
                new UpperCaseAdvice()
        );
        ProxyFactoryBean<Hello> factoryBean = new ProxyFactoryBean<>(target, advisor);

        Hello actual = factoryBean.getObject();
        assertAll(
                () -> assertThat(actual.sayHello("jinyoung")).isEqualTo("SAY JINYOUNG"),
                () -> assertThat(actual.pingPong("jinyoung")).isEqualTo("pingPong jinyoung")
        );
    }

    @Test
    void 프록시빈의_타입을_반환한다() {
        Target<Hello> target = new Target<>(new Hello(), new Object[0]);
        Advisor advisor = new Advisor(
                method -> method.getName().startsWith("say"),
                new UpperCaseAdvice()
        );
        ProxyFactoryBean<Hello> factoryBean = new ProxyFactoryBean<>(target, advisor);

        Class<Hello> actual = factoryBean.getType();
        assertThat(actual).isEqualTo(Hello.class);
    }

    public static class Hello {

        public String sayHello(String name) {
            return "say " + name;
        }

        public String pingPong(String name) {
            return "pingPong " + name;
        }
    }

    private static class UpperCaseAdvice implements Advice {

        @Override
        public Object invoke(JoinPoint joinPoint) throws Throwable {
            return ((String) joinPoint.proceed()).toUpperCase();
        }
    }
}
