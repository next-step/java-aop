package com.interface21.beans.factory.support;

import com.interface21.beans.BeanUtils;
import com.interface21.beans.factory.ConfigurableListableBeanFactory;
import com.interface21.beans.factory.FactoryBean;
import com.interface21.beans.factory.TransactionProxyCreator;
import com.interface21.beans.factory.config.BeanDefinition;
import com.interface21.context.annotation.AnnotatedBeanDefinition;
import com.interface21.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultListableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private static final Logger log = LoggerFactory.getLogger(DefaultListableBeanFactory.class);

    private final Map<Class<?>, Object> beans = new HashMap<>();

    private final Map<Class<?>, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public void preInstantiateSingletons() {
        for (Class<?> clazz : getBeanClasses()) {
            getBean(clazz);
        }
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanDefinitions.keySet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        Object bean = beans.get(clazz);
        if (bean != null) {
            return (T) bean;
        }

        BeanDefinition beanDefinition = beanDefinitions.get(clazz);
        if (beanDefinition instanceof AnnotatedBeanDefinition annotatedBeanDefinition) {
            bean = getObjectForBeanInstance(createAnnotatedBean(annotatedBeanDefinition));
            bean = postProcessIfNecessary(bean);

            beans.put(bean.getClass(), bean);
            initialize(bean, clazz);
            return (T) bean;
        }

        Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses());
        if (concreteClazz.isEmpty()) {
            return null;
        }

        beanDefinition = beanDefinitions.get(concreteClazz.get());
        log.debug("BeanDefinition : {}", beanDefinition);

        bean = getObjectForBeanInstance(inject(beanDefinition));
        bean = postProcessIfNecessary(bean);
        beans.put(bean.getClass(), bean);

        initialize(bean, concreteClazz.get());
        return (T) bean;
    }

    private Object getObjectForBeanInstance(final Object beanInstance) {
        if (beanInstance instanceof FactoryBean<?> factoryBean) {
            try {
                return factoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("FactoryBean.getObject() 실행 실패", e);//fixme: 커스터마이징
            }
        }

        return beanInstance;
    }

    private Object postProcessIfNecessary(Object bean) {
        if (hasTransactionalAnnotationAtLeastOne(bean)) {
            TransactionProxyCreator transactionProxyCreator = new TransactionProxyCreator(getBean(DataSource.class));
            bean = transactionProxyCreator.postInitialization(bean);
        }
        return bean;
    }

    private boolean hasTransactionalAnnotationAtLeastOne(final Object bean) {
        Class<?> beanType = bean.getClass();
        return hasAnnotationOnClass(beanType) || hasAnnotationOnMethod(beanType);
    }

    private boolean hasAnnotationOnClass(final Class<?> beanType) {
        return beanType.getAnnotation(Transactional.class) != null;
    }

    private boolean hasAnnotationOnMethod(final Class<?> beanType) {
        return Arrays.stream(beanType.getDeclaredMethods())
                .anyMatch(method -> method.getAnnotation(Transactional.class) != null);
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

    private Object createAnnotatedBean(AnnotatedBeanDefinition annotatedBeanDefinition) {
        Method method = annotatedBeanDefinition.getMethod();
        Object bean = getBean(method.getDeclaringClass());
        Object[] args = populateArguments(method.getParameterTypes());
        return BeanFactoryUtils.invokeMethod(method, bean, args)
                .orElseThrow(() -> new IllegalArgumentException("@Bean 메서드에 잘못된 값이 입력되었습니다. method=%s, bean=%s, args=%s".formatted(method.getName(), bean.getClass(), Arrays.toString(args))));
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
