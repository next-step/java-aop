package com.interface21.framework;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CglibAopProxy 테스트")
class CglibAopProxyTest {

    private CglibAopProxy cglibAopProxy;

    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    final PrintStream standardOut = System.out;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Advised advised = new Advised(HelloService.class);
        cglibAopProxy = new CglibAopProxy(advised);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @DisplayName("프록시 타입의 클래스를 이름을 확인한다.")
    @Test
    void testGetProxyClass() {
        // given
        Object proxy = cglibAopProxy.getProxy();

        // when
        Class<?> proxyClass = proxy.getClass();

        // then
        assertEquals("com.interface21.framework.HelloService$$EnhancerByCGLIB$$76fd2896", proxyClass.getName());
    }

    @DisplayName("프록시 객체를 생성하고 메서드를 호출한다.")
    @Test
    void testGetProxy() {
        // given
        HelloService proxy = (HelloService) cglibAopProxy.getProxy();

        // when
        String result = proxy.sayHello("World");

        // then
        assertEquals("Hello, World", result);
    }

    @DisplayName("등록된 advice를 통해 메서드 호출 전/후에 로직을 수행한다.")
    @Test
    void testGetProxyWithAdvice() {
        // given
        Advised advised = new Advised(HelloService.class);
        advised.addAdvisor(new Advisor((method) -> true, new UpperCaseAdvice()));
        advised.addAdvisor(new Advisor((method) -> true, new LoggingAdvice()));
        CglibAopProxy cglibAopProxy = new CglibAopProxy(advised);
        HelloService proxy = (HelloService) cglibAopProxy.getProxy();

        // when
        String result = proxy.sayHello("World");

        // then
        assertEquals("Method name: sayHello\n", outputStreamCaptor.toString());
        assertEquals("HELLO, WORLD", result);
    }

    public static class UpperCaseAdvice implements AfterReturningAdvice {
        @Override
        public void invoke(MethodInvocation invocation) throws Throwable {
            String result = (String) invocation.getReturnValue();
            invocation.setReturnValue(result.toUpperCase());
        }
    }

    public static class LoggingAdvice implements BeforeAdvice {
        @Override
        public void invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("Method name: " + invocation.getMethod().getName());
        }
    }
}
