package com.interface21.beans.factory;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.beans.factory.config.AdvisedSupport;
import com.interface21.beans.factory.proxy.DefaultAdvisor;
import com.interface21.beans.factory.proxy.advice.AfterReturningAdvice;
import com.interface21.beans.factory.proxy.advice.BeforeAdvice;
import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.cglib.CglibAopProxy;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import com.interface21.beans.factory.proxy.pointcut.TrueMethodMatcher;
import java.lang.reflect.Method;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CglibAopProxyTest {

    private static final Logger log = LoggerFactory.getLogger(CglibAopProxyTest.class);
    private final Advised advised = new AdvisedSupport();
    private final LogCaptor logCaptor = LogCaptor.forClass(CglibAopProxyTest.class);

    @BeforeEach
    void setUp() {
        advised.setTarget(Sample.class);
    }

    @Test
    @DisplayName("Advised에 대한 Proxy 객체를 만듭니다.")
    void createProxyObject() {

        advised.addAdvisor(new DefaultAdvisor(new TrueMethodMatcher(), new Interceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object hello = invocation.proceed();

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

        advised.addAdvisor(new DefaultAdvisor(new TrueMethodMatcher(), new BeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                log.info("hello");
            }

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object hello = invocation.proceed();

                return hello.toString().toUpperCase();
            }
        }));

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advised);
        Sample proxy = (Sample) cglibAopProxy.getProxy();
        LogCaptor logCaptor = LogCaptor.forClass(CglibAopProxyTest.class);

        assertThat(cglibAopProxy.getProxy().getClass().toString()).contains("CGLIB");
        assertThat(proxy.hi()).isEqualTo("HI");
        assertThat(logCaptor.getLogs())
            .hasSize(1)
            .contains("hello");
    }

    @Test
    @DisplayName("AfterAdvice를 등록하여 수행합니다.")
    void registerAdviceAfter() {

        advised.addAdvisor(new DefaultAdvisor(new TrueMethodMatcher(), new AfterReturningAdvice() {
            @Override
            public void afterReturning(Object returned, Method method, Object[] args,
                Object target) {
                log.info("nice to meet you");
            }

            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object hello = invocation.proceed();

                return hello.toString().toUpperCase();
            }
        }));

        CglibAopProxy cglibAopProxy = new CglibAopProxy(advised);
        Sample proxy = (Sample) cglibAopProxy.getProxy();

        assertThat(cglibAopProxy.getProxy().getClass().toString()).contains("CGLIB");
        assertThat(proxy.hi()).isEqualTo("HI");
        assertThat(logCaptor.getLogs())
            .hasSize(1)
            .contains("nice to meet you");
    }


}
