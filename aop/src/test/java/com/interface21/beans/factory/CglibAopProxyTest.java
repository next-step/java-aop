package com.interface21.beans.factory;

import com.interface21.beans.factory.config.AdvisedSupport;
import com.interface21.beans.factory.proxy.DefaultAdvisor;
import com.interface21.beans.factory.proxy.advice.BeforeAdvice;
import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.cglib.CglibAopProxy;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import com.interface21.beans.factory.proxy.pointcut.TrueMethodMatcher;
import com.interface21.framework.AopProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CglibAopProxyTest {

    @Test
    @DisplayName("Advised에 대한 Proxy 객체를 만듭니다.")
    void createProxyObject() {
        Advised advised = new AdvisedSupport();
        advised.setTarget(Sample.class);

        advised.addAdvisor(new DefaultAdvisor(new TrueMethodMatcher(), new Interceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object hello =  invocation.proceed();

                return hello.toString().toUpperCase();
            }
        }));

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advised);
        Sample proxy = (Sample) cglibAopProxy.getProxy();

        assertThat(cglibAopProxy.getProxy().getClass().toString()).contains("CGLIB");
        assertThat(proxy.hi()).isEqualTo("HI");
    }

    @Test
    @DisplayName("BeforeAdvice를 등록하여 수행합니다.")
    void registerAdviceBefore() {
        Logger mockLogger = mock(Logger.class);
        BusinessWorker worker = new BusinessWorker(mockLogger);

        worker.generateLogs("Test message");

        Advised advised = new AdvisedSupport();
        advised.setTarget(Sample.class);

        advised.addAdvisor(new DefaultAdvisor(new TrueMethodMatcher(), new BeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                log.
            }

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object hello =  invocation.proceed();

                return hello.toString().toUpperCase();
            }
        }));

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advised);
        Sample proxy = (Sample) cglibAopProxy.getProxy();

        assertThat(cglibAopProxy.getProxy().getClass().toString()).contains("CGLIB");
        assertThat(proxy.hi()).isEqualTo("HI");
    }

    @Test
    @DisplayName("AfterAdvice를 등록하여 수행합니다.")
    void registerAdviceAfter() {
        Advised advised = new AdvisedSupport();
        advised.setTarget(Sample.class);

        advised.addAdvisor(new DefaultAdvisor(new TrueMethodMatcher(), new Interceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object hello =  invocation.proceed();

                return hello.toString().toUpperCase();
            }
        }));

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advised);
        Sample proxy = (Sample) cglibAopProxy.getProxy();

        assertThat(cglibAopProxy.getProxy().getClass().toString()).contains("CGLIB");
        assertThat(proxy.hi()).isEqualTo("HI");
    }


}
