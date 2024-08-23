package com.interface21.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.beans.factory.config.BeanDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.SampleBean;
import samples.SampleFactoryBean;

class DefaultListableBeanFactoryTest {

    private DefaultListableBeanFactory beanFactory;

    @BeforeEach
    void setUp() {
        beanFactory = new DefaultListableBeanFactory();
    }

    @Test
    @DisplayName("FactoryBean을 BeanDefinition으로 가지고 있으면 FactoryBean을 통해 Bean을 생성한다.")
    void testFactoryBean() {
        BeanDefinition beanDefinition = new GenericBeanDefinition(SampleFactoryBean.class);
        beanFactory.registerBeanDefinition(SampleBean.class, beanDefinition);

        SampleBean sampleBean = beanFactory.getBean(SampleBean.class);

        assertThat(sampleBean.getMessage()).isEqualTo(SampleFactoryBean.MESSAGE);
    }
}
