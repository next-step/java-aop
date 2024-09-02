package com.interface21.beans.factory.support;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.beans.factory.config.BeanDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.Sample;
import samples.SampleFactoryBean;

class DefaultListableBeanFactoryTest {

    @DisplayName("beanFactory로 객체를 빈으로 등록합니다.")
    @Test
    void setBeanFactory() {
        BeanDefinition beanDefinition = new GenericBeanDefinition(SampleFactoryBean.class);

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerBeanDefinition(Sample.class, beanDefinition);

        Sample sample = beanFactory.getBean(Sample.class);

        assertThat(sample.getClass()).isEqualTo(Sample.class);
    }

}
