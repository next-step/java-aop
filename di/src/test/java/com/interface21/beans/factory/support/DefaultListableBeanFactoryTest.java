package com.interface21.beans.factory.support;

import com.interface21.beans.factory.config.BeanDefinition;
import com.interface21.beans.factory.config.BeanPostProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.HelloTarget;
import samples.HelloTargetFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class DefaultListableBeanFactoryTest {

    @Test
    @DisplayName("FactoryBean 을 등록 후 bean 으로 조회할 수 있다.")
    void getFactoryBeanTest() {
        final BeanDefinition beanDefinition = new GenericBeanDefinition(HelloTargetFactoryBean.class);

        final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition(HelloTarget.class, beanDefinition);

        final HelloTarget helloTarget = beanFactory.getBean(HelloTarget.class);

        assertThat(helloTarget.sayHello("jongmin")).isEqualTo("Hello jongmin");
    }

    @Test
    @DisplayName("FactoryBean 에 BeanPostProcessor 를 등록하면 bean 등록 시 호출된다.")
    void beanPostProcessorTest() {
        final BeanDefinition beanDefinition = new GenericBeanDefinition(HelloTargetFactoryBean.class);

        final DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        final FakeBeanPostProcessor beanPostProcessor = new FakeBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);
        beanFactory.registerBeanDefinition(HelloTarget.class, beanDefinition);

        final HelloTarget helloTarget = beanFactory.getBean(HelloTarget.class);

        assertSoftly(softly -> {
            softly.assertThat(helloTarget.sayHello("jongmin")).isEqualTo("Hello jongmin");
            softly.assertThat(beanPostProcessor.isCalled).isTrue();
        });
    }

    static class FakeBeanPostProcessor implements BeanPostProcessor {

        private boolean isCalled = false;

        @Override
        public Object postInitialization(final Object bean) {
            isCalled = true;
            return bean;
        }
    }
}
