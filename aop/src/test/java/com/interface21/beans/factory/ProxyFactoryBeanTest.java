package com.interface21.beans.factory;

import com.interface21.beans.factory.config.AdvisedSupport;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.DefaultAdvisor;
import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.factory.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.joinpoint.MethodInvocation;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;
import com.interface21.beans.factory.proxy.pointcut.TrueMethodMatcher;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyFactoryBeanTest {

    @Test
    @DisplayName("Cglib 프록시 객체 생성")
    void createProxyObject() {
        Pointcut pointCut = new TrueMethodMatcher();
        Interceptor methodInterceptor = new LogInterceptor();

        Advisor advisor = new DefaultAdvisor(pointCut, methodInterceptor);

        Advised advisedSupport = new AdvisedSupport();
        advisedSupport.addAdvisor(advisor);
        advisedSupport.setTarget(Sample.class);

        ProxyFactoryBean<Sample> proxyFactoryBean = new ProxyFactoryBean<>(Sample.class,
            advisedSupport);

        Sample sample = proxyFactoryBean.getObject();

        assertThat(sample).isInstanceOf(Sample.class);
        assertThat(Enhancer.isEnhanced(sample.getClass())).isTrue();
        assertThat(sample.hi()).isEqualTo("HI");
    }


    public class LogInterceptor implements Interceptor {
        private static final Logger LOGGER = Logger.getLogger(LogInterceptor.class.getName());

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Object returned = invocation.proceed();

            return returned.toString().toUpperCase();
        }
    }
}
