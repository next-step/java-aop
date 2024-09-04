package com.interface21.beans.factory.support;

import com.interface21.beans.BeanInstantiationException;
import com.interface21.beans.BeanUtils;
import com.interface21.beans.factory.BeanFactory;
import com.interface21.beans.factory.BeanPostProcessor;
import com.interface21.beans.factory.ConfigurableListableBeanFactory;
import com.interface21.beans.factory.FactoryBean;
import com.interface21.beans.factory.config.BeanDefinition;
import com.interface21.context.annotation.AnnotatedBeanDefinition;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DefaultListableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private static final Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);

    private final Map<Class<?>, Object> beans = new HashMap<>();

    private final Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();
    private final List<BeanPostProcessor> postProcessors = new ArrayList<>();

    @Override
    public void preInstantiateSingletons() {
        registerBean(BeanFactory.class, this);

        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
    }

    private Object postProcess(Object bean) {
        var result = bean;
        for (BeanPostProcessor beanPostProcessor : postProcessors) {
            result = unwrapFactoryBean(beanPostProcessor.postInitialization(result));
        }
        return result;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        postProcessors.add(beanPostProcessor);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object oldBean = beans.get(clazz);
        if (oldBean != null) {
            return (T) oldBean;
        }

        BeanDefinition beanDefinition = beanDefinitions.get(clazz);
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            Object createdBean = createAnnotatedBean(beanDefinition);
            if (createdBean == null) {
                return null;
            }
            Object bean = postProcess(unwrapFactoryBean(createdBean));
            return (T) registerBean(getBeanType(bean), bean);
        }

        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses());
        if (concreteClazz.isEmpty()) {
            return null;
        }

        Object createdBean = postProcess(unwrapFactoryBean(createGeneralBean(concreteClazz.get())));
        return (T) registerBean(getBeanType(createdBean), createdBean);
    }

    private void initialize(Object bean, Class<?> beanClass) {
        Set<Method> initializeMethods = BeanFactoryUtils.getBeanMethods(beanClass, PostConstruct.class);
        if (initializeMethods.isEmpty()) {
            return;
        }
        for (Method initializeMethod : initializeMethods) {
            log.debug("@PostConstruct Initialize Method : {}", initializeMethod);
            BeanFactoryUtils.invokeMethod(initializeMethod, bean,
                populateArguments(initializeMethod.getParameterTypes()));
        }
    }

    private Object createAnnotatedBean(BeanDefinition beanDefinition) {
        final var method = ((AnnotatedBeanDefinition) beanDefinition).getMethod();
        final var bean = getBean(method.getDeclaringClass());
        final var args = populateArguments(method.getParameterTypes());
        return BeanFactoryUtils.invokeMethod(method, bean, args).orElse(null);
    }

    private Object createGeneralBean(Class<?> aClass) {
        BeanDefinition beanDefinition = beanDefinitions.get(aClass);
        log.debug("BeanDefinition : {}", beanDefinition);
        return inject(beanDefinition);
    }

    private Object[] populateArguments(Class<?>[] paramTypes) {
        List<Object> args = new ArrayList<>();
        for (Class<?> param : paramTypes) {
            Object bean = getBean(param);
            if (bean == null) {
                throw new NullPointerException(param + "에 해당하는 Bean이 존재하지 않습니다.");
            }
            args.add(getBean(param));
        }
        return args.toArray();
    }

    private Object inject(BeanDefinition beanDefinition) {
        if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
            return BeanUtils.instantiate(beanDefinition.getBeanClass());
        } else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD) {
            return injectFields(beanDefinition);
        } else {
            return injectConstructor(beanDefinition);
        }
    }

    private Object injectFields(BeanDefinition beanDefinition) {
        Object bean = BeanUtils.instantiate(beanDefinition.getBeanClass());
        Set<Field> injectFields = beanDefinition.getInjectFields();
        for (Field field : injectFields) {
            injectField(bean, field);
        }
        return bean;
    }

    private void injectField(Object bean, Field field) {
        log.debug("Inject Bean : {}, Field : {}", bean, field);
        try {
            field.setAccessible(true);
            field.set(bean, getBean(field.getType()));
        } catch (IllegalAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
    }

    private Object injectConstructor(BeanDefinition beanDefinition) {
        Constructor<?> constructor = beanDefinition.getInjectConstructor();
        Object[] args = populateArguments(constructor.getParameterTypes());
        return BeanUtils.instantiateClass(constructor, args);
    }

    private Class<?> getBeanType(Object bean) {
        if (bean instanceof final FactoryBean<?> factoryBean) {
            return factoryBean.getObjectType();
        }
        return bean.getClass();
    }

    private Object unwrapFactoryBean(Object createdBean) {
        if (createdBean instanceof FactoryBean<?> factoryBean) {
            try {
                Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(factoryBean.getObjectType());

                if (injectedConstructor == null) {
                    return factoryBean.getObject();
                }

                final Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
                return factoryBean.getObject(parameterTypes, populateArguments(parameterTypes));
            } catch (Exception e) {
                throw new BeanInstantiationException(createdBean.getClass(), "FactoryBean 객체 처리에 실패", e);
            }
        }
        return createdBean;
    }

    private Object registerBean(Class<?> clazz, Object createdBean) {
        beans.put(clazz, createdBean);
        initialize(createdBean, clazz);
        return createdBean;
    }

    @Override
    public void clear() {
        beanDefinitions.clear();
        beans.clear();
    }

    @Override
    public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
        log.debug("register bean : {}", clazz);
        beanDefinitions.put(clazz, beanDefinition);
    }
}
