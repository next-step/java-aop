package com.interface21.beans.factory.support;

import com.interface21.beans.factory.config.BeanDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.HelloTarget;
import samples.HelloTargetFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;

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

}
