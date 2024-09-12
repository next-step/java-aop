package com.interface21.beans.factory;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.beans.factory.config.AdvisedSupport;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.DefaultAdvisor;
import com.interface21.beans.factory.proxy.advice.AfterReturningAdvice;
import com.interface21.beans.factory.proxy.advice.BeforeAdvice;
import com.interface21.beans.factory.proxy.factory.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;
import com.interface21.beans.factory.proxy.pointcut.TrueMethodMatcher;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProxyFactoryBeanTest {

    @Test
    @DisplayName("Cglib 프록시 객체 생성")
    void createProxyObject() {
        Pointcut pointCut = new TrueMethodMatcher();
        MethodInterceptor methodInterceptor = new LogInterceptor();

        Advisor advisor = new DefaultAdvisor(pointCut, methodInterceptor);

        Advised advisedSupport = new AdvisedSupport();
        advisedSupport.addAdvisor(advisor);
        advisedSupport.setTarget(Sample.class);

        ProxyFactoryBean<Sample> proxyFactoryBean = new ProxyFactoryBean<>(Sample.class,
            advisedSupport);

        Sample sample = proxyFactoryBean.getObject();

        assertThat(sample).isInstanceOf(Sample.class);
        assertThat(sample.hi()).isEqualTo("HI");
        assertThat(Enhancer.isEnhanced(sample.getClass())).isTrue();
    }


    public class LogInterceptor implements AfterReturningAdvice {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {
            Object returnValue = proxy.invokeSuper(obj, args);

            return returnValue.toString().toUpperCase();
        }



        @Override
        public void afterReturning(Object returned, Method method, Object[] args, Object target) {
            System.out.println("hell");

        }
    }
}
