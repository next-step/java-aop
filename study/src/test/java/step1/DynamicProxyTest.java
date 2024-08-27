package step1;

import camp.nextstep.step1.HelloMethodMatcher;
import camp.nextstep.step1.dynamic.Hello;
import camp.nextstep.step1.dynamic.HelloInvocationHandler;
import camp.nextstep.step1.dynamic.HelloTarget;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicProxyTest {
    @Test
    void 프록시를_생성해서_메소드호출시_대문자로_반환한다() {
        // given
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[] {Hello.class},
                new HelloInvocationHandler(new HelloTarget(), new HelloMethodMatcher())
        );

        // when
        String sayHelloResult = proxyInstance.sayHello("a");
        String sayHiResult = proxyInstance.sayHi("b");
        String sayThankYouResult = proxyInstance.sayThankYou("c");

        // then
        assertEquals("HELLO A", sayHelloResult);
        assertEquals("HI B", sayHiResult);
        assertEquals("THANK YOU C", sayThankYouResult);
    }

    @Test
    void say로_시작하는_메소드만_대문자로_반환한다() {
        // given
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                DynamicProxyTest.class.getClassLoader(),
                new Class[] {Hello.class},
                new HelloInvocationHandler(new HelloTarget(), new HelloMethodMatcher())
        );

        // when
        String sayHelloResult = proxyInstance.sayHello("a");
        String sayHiResult = proxyInstance.sayHi("b");
        String sayThankYouResult = proxyInstance.sayThankYou("c");
        String pingpong = proxyInstance.pingpong("d");

        // then
        assertEquals("HELLO A", sayHelloResult);
        assertEquals("HI B", sayHiResult);
        assertEquals("THANK YOU C", sayThankYouResult);
        assertEquals("Pong d", pingpong);
    }
}
