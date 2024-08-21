package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.BeanFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

class ProxyFactoryBeanTest {

    private final BeanFactory beanFactory = mock(BeanFactory.class);

    @Test
    void 프록시빈을_생성한다() throws NoSuchMethodException {
        Target<Hello> target = new Target<>(new Hello(), Hello.class.getConstructor());
        Advisor advisor = new Advisor(
                method -> method.getName().startsWith("say"),
                new UpperCaseAdvice()
        );
        ProxyFactoryBean<Hello> factoryBean = new ProxyFactoryBean<>(target, advisor, beanFactory);

        Hello actual = factoryBean.getObject();
        assertAll(
                () -> assertThat(actual.sayHello("jinyoung")).isEqualTo("SAY JINYOUNG"),
                () -> assertThat(actual.pingPong("jinyoung")).isEqualTo("pingPong jinyoung")
        );
    }

    @Test
    void 프록시빈의_타입을_반환한다() throws NoSuchMethodException {
        Target<Hello> target = new Target<>(new Hello(), Hello.class.getConstructor());
        Advisor advisor = new Advisor(
                method -> method.getName().startsWith("say"),
                new UpperCaseAdvice()
        );
        ProxyFactoryBean<Hello> factoryBean = new ProxyFactoryBean<>(target, advisor, beanFactory);

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
