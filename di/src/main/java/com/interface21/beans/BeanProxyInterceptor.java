package com.interface21.beans;

import com.interface21.beans.factory.BeanFactory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class BeanProxyInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(BeanProxyInterceptor.class);

    private Class<?> type;
    private BeanFactory beanFactory;
    private Object target;

    public BeanProxyInterceptor(Class<?> type, BeanFactory beanFactory, Object target) {
        this.type = type;
        this.beanFactory = beanFactory;
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (target == null) {
            logger.info("빈 초기화");
            target = beanFactory.getBean(type);
        }

        logger.info("프록시 메소드 동작");
        return method.invoke(this.target, args);
    }
}
