package com.interface21.beans.factory.support;

import com.interface21.beans.factory.config.BeanPostProcessor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultListableBeanFactoryTest {

    @Test
    void bean을_여러개의_BeanPostProcess에서_처리가능할_경우_에외를_던진다() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanPostProcessor(new AlwaysAcceptBeanPostProcessor());
        beanFactory.registerBeanPostProcessor(new AlwaysAcceptBeanPostProcessor());
        beanFactory.registerBeanDefinition(Hello.class, new GenericBeanDefinition(Hello.class));

        assertThatThrownBy(() -> beanFactory.getBean(Hello.class))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void bean이_처리가능한_BeanPostProcess가_없는_경우_해당_bean객체를_저장한다() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition(Hello.class, new GenericBeanDefinition(Hello.class));

        Hello actual = beanFactory.getBean(Hello.class);
        assertThat(actual).isNotNull();
    }

    @Test
    void bean이_처리가능한_BeanPostProcess가_있는_경우_처리된_bean객체를_저장한다() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanPostProcessor(new NullBeanPostProcessor());
        beanFactory.registerBeanDefinition(Hello.class, new GenericBeanDefinition(Hello.class));

        Hello actual = beanFactory.getBean(Hello.class);
        assertThat(actual).isNull();
    }

    public static class Hello {
    }

    private static class NullBeanPostProcessor implements BeanPostProcessor {

        @Override
        public boolean accept(Object bean) {
            return true;
        }

        @Override
        public Object postInitialization(Object bean) {
            return null;
        }
    }

    private static class AlwaysAcceptBeanPostProcessor implements BeanPostProcessor {

        @Override
        public boolean accept(Object bean) {
            return true;
        }

        @Override
        public Object postInitialization(Object bean) {
            return bean;
        }
    }
}
