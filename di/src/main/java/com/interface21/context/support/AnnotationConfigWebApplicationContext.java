package com.interface21.context.support;

import com.interface21.beans.config.BeanPostConstructProcessor;
import com.interface21.beans.config.TransactionBeanPostProcessor;
import com.interface21.beans.factory.config.BeanFactoryBeanPostProcessor;
import com.interface21.beans.factory.support.DefaultListableBeanFactory;
import com.interface21.context.ApplicationContext;
import com.interface21.context.annotation.AnnotatedBeanDefinitionReader;
import com.interface21.context.annotation.ClassPathBeanDefinitionScanner;
import com.interface21.context.annotation.ComponentScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotationConfigWebApplicationContext implements ApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(AnnotationConfigWebApplicationContext.class);

    private final DefaultListableBeanFactory beanFactory;

    public AnnotationConfigWebApplicationContext(Class<?>... annotatedClasses) {
        beanFactory = initBeanFactory(annotatedClasses);
        beanFactory.preInstantiateSingletons();
    }

    public AnnotationConfigWebApplicationContext(final BeanFactoryBeanPostProcessor beanPostProcessor, final Class<?>... annotatedClasses) {
        beanFactory = initBeanFactory(annotatedClasses);
        beanFactory.addBeanPostProcessor(beanPostProcessor);
        beanPostProcessor.injectBeanFactory(beanFactory);

        beanFactory.preInstantiateSingletons();
    }

    private DefaultListableBeanFactory initBeanFactory(final Class<?>[] annotatedClasses) {
        final DefaultListableBeanFactory beanFactory;
        Object[] basePackages = findBasePackages(annotatedClasses);
        beanFactory = new DefaultListableBeanFactory();
        beanFactory.addBeanPostProcessor(new BeanPostConstructProcessor(beanFactory));
        beanFactory.addBeanPostProcessor(new TransactionBeanPostProcessor(beanFactory));
        final var beanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(annotatedClasses);

        if (basePackages.length > 0) {
            final var scanner = new ClassPathBeanDefinitionScanner(beanFactory);
            scanner.doScan(basePackages);
        }
        return beanFactory;
    }

    private Object[] findBasePackages(Class<?>[] annotatedClasses) {
        List<Object> basePackages = new ArrayList<>();
        for (Class<?> annotatedClass : annotatedClasses) {
            ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            if (componentScan == null) {
                continue;
            }
            for (String basePackage : componentScan.value()) {
                log.info("Component Scan basePackage : {}", basePackage);
            }
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
