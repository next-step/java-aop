package com.interface21.beans.factory.config;

import com.interface21.beans.factory.proxy.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FactoryBeanPostProcessorTest {
    
    private final FactoryBeanPostProcessor beanPostProcessor = new FactoryBeanPostProcessor();

    @Test
    void FactoryBean에_대해서_지원한다() {
        Target<Hello> target = new Target<>(new Hello(), new Object[0]);
        Advisor advisor = new Advisor(
                method -> method.getName().startsWith("say"),
                new UpperCaseAdvice()
        );
        ProxyFactoryBean<Hello> factoryBean = new ProxyFactoryBean<>(target, advisor);

        boolean actual = beanPostProcessor.accept(factoryBean);
        assertThat(actual).isTrue();
    }

    @Test
    void FactoryBean이_아닌_bean은_지원하지_않는다() {
        boolean actual = beanPostProcessor.accept(new Hello());
        assertThat(actual).isFalse();
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
