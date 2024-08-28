package com.interface21.beans.factory;

import code.AopTestBeanFactory;
import com.interface21.beans.factory.proxy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProxyFactoryBeanTest {

    private final BeanFactory beanFactory = new AopTestBeanFactory();

    @DisplayName("target 클래스의 타입을 반환 한다")
    @Test
    public void getObjectType() throws Exception {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));

        // when
        final Class<?> actual = proxyFactoryBean.getObjectType();

        // then
        assertThat(actual).isEqualTo(TargetBean.class);
    }

    @DisplayName("target 클래스를 주입 받아 해당 클래스 타입의 프록시를 생성 한다")
    @Test
    public void getObject() throws Exception {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));

        // when
        final TargetBean actual = (TargetBean) proxyFactoryBean.getObject();

        // then
        assertThat(actual).isInstanceOf(TargetBean.class);
    }

    @DisplayName("프록시를 생성할 때, 등록 된 advisor 가 없다면 메서드를 바로 실행 한다")
    @Test
    public void invokeProxyMethodWithoutAdvisor() throws Exception {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));
        final TargetBean proxy = (TargetBean) proxyFactoryBean.getObject();

        // when then
        assertDoesNotThrow(() -> proxy.hello());
    }

    @DisplayName("프록시를 생성할 때, 등록 된 MethodBeforeAdvice 가 있다면 advice 호출 후 메서드를 실행 한다")
    @Test
    public void invokeProxyMethodWithMethodBeforeAdvice() throws Throwable {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));
        final MethodBeforeAdvice beforeAdvice1 = mock();
        final MethodBeforeAdvice beforeAdvice2 = mock();
        final Advisor advisor1 = new Advisor(beforeAdvice1);
        final Advisor advisor2 = new Advisor(beforeAdvice2);
        proxyFactoryBean.addAdvisors(advisor1, advisor2);
        final TargetBean proxy = (TargetBean) proxyFactoryBean.getObject();

        // when
        proxy.hello();

        // then
        assertAll(
                () -> verify(beforeAdvice1, times(1)).invoke(any(), any(), any()),
                () -> verify(beforeAdvice2, times(1)).invoke(any(), any(), any())
        );
    }

    @DisplayName("프록시를 생성할 때, 등록 된 AfterRunningAdvice 가 있다면 메서드 호출 후 advice 를 실행 한다")
    @Test
    public void invokeProxyMethodWithAfterRunningAdvice() throws Throwable {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));
        final AfterReturningAdvice afterAdvice1 = mock();
        final AfterReturningAdvice afterAdvice2 = mock();
        final Advisor advisor1 = new Advisor(afterAdvice1);
        final Advisor advisor2 = new Advisor(afterAdvice2);
        proxyFactoryBean.addAdvisors(advisor1, advisor2);
        final TargetBean proxy = (TargetBean) proxyFactoryBean.getObject();

        // when
        proxy.hello();

        // then
        assertAll(
                () -> verify(afterAdvice1, times(1)).afterReturning(any(), any(), any(), any()),
                () -> verify(afterAdvice2, times(1)).afterReturning(any(), any(), any(), any())
        );
    }

    @DisplayName("프록시를 생성할 때, 등록 된 ThrowsAdvice 가 있다면 메서드 중 예외가 던져지면 advice 를 실행 한다")
    @Test
    public void invokeProxyMethodWitThrowsAdvice() throws Throwable {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));
        final List<Exception> exceptions = new ArrayList<>();
        final ThrowsAdvice throwsAdvice = exceptions::add;
        final Advisor advisor = new Advisor(throwsAdvice);
        proxyFactoryBean.addAdvisors(advisor);
        final TargetBean proxy = (TargetBean) proxyFactoryBean.getObject();

        // when then
        assertAll(
                () -> assertThatThrownBy(proxy::throwHello),
                () -> assertThat(exceptions).isNotEmpty()
        );
    }

    @DisplayName("프록시 메서드를 호출할 때 등록된 Advice 가 존재 해도 Pointcut 조건에 만족하지 않으면 advice 는 실행 되지 않는다")
    @Test
    public void invokeProxyMethodWithNotMatchAdvice() throws Throwable {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));
        final Pointcut falsePointcut = (method, targetClass) -> false;
        final AfterReturningAdvice advice1 = mock();
        final AfterReturningAdvice advice2 = mock();
        final Advisor advisor1 = new Advisor(advice1, falsePointcut);
        final Advisor advisor2 = new Advisor(advice2, falsePointcut);
        proxyFactoryBean.addAdvisors(advisor1, advisor2);
        final TargetBean proxy = (TargetBean) proxyFactoryBean.getObject();

        // when
        proxy.hello();

        // then
        assertAll(
                () -> verify(advice1, times(0)).afterReturning(any(), any(), any(), any()),
                () -> verify(advice2, times(0)).afterReturning(any(), any(), any(), any())
        );
    }

    @DisplayName("프록시 메서드를 호출할 때 MethodBeforeAdvice 는 AfterRunningAdvice 보다 먼저 호출 된다")
    @Test
    public void invokeAdviceOrder() throws Throwable {
        // given
        final ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean(beanFactory);
        proxyFactoryBean.setTargetClass(new TypeTarget<>(TargetBean.class));
        final MethodBeforeAdvice beforeAdvice = mock();
        final AfterReturningAdvice afterAdvice = mock();
        final Advisor advisor1 = new Advisor(beforeAdvice);
        final Advisor advisor2 = new Advisor(afterAdvice);
        proxyFactoryBean.addAdvisors(advisor1, advisor2);
        final TargetBean proxy = (TargetBean) proxyFactoryBean.getObject();

        // when
        proxy.hello();

        // then
        final InOrder inOrder = inOrder(beforeAdvice, afterAdvice);
        assertAll(
                () -> inOrder.verify(beforeAdvice).invoke(any(), any(), any()),
                () -> inOrder.verify(afterAdvice).afterReturning(any(), any(), any(), any())
        );
    }

    public static class TargetBean {
        String hello() {
            return "hello";
        }

        String throwHello() throws RuntimeException {
            throw new RuntimeException();
        }
    }

}
