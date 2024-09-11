package com.interface21.beans.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import com.interface21.beans.factory.proxy.factory.ProxyFactoryBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProxyFactoryBeanTest {

    @Test
    @DisplayName("Cglib 프록시 객체 생성")
    void createProxyObject() {
        ProxyFactoryBean<Sample> proxyFactoryBean = new ProxyFactoryBean<>(Sample.class,
            new LogInterceptor());

        Sample sample = proxyFactoryBean.getObject();

        assertThat(sample).isInstanceOf(Sample.class);
        assertThat(sample.hi()).isEqualTo("HI");
        assertThat(Enhancer.isEnhanced(sample.getClass())).isTrue();
    }


    public class LogInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {
            Object returnValue = proxy.invokeSuper(obj, args);

            return returnValue.toString().toUpperCase();
        }
    }
}
