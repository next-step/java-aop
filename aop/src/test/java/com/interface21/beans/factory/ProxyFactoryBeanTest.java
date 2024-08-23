package com.interface21.beans.factory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sample.HelloTarget;

class ProxyFactoryBeanTest {

    @Test
    void testProxyFactoryBean() {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(HelloTarget.class);
        Advice advice = Mockito.mock(Advice.class);
        PointCut pointCut = Mockito.mock(PointCut.class);

        proxyFactoryBean.setAdvice(advice);
        proxyFactoryBean.setPointCut(pointCut);
        when(pointCut.matches(any())).thenReturn(true);

        HelloTarget helloTarget = (HelloTarget) proxyFactoryBean.getObject();

        helloTarget.sayHello("test");

        verify(pointCut, times(2)).matches(any());
        verify(advice, times(1)).before();
        verify(advice, times(1)).after();
    }
}
