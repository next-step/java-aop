package step1;

import camp.nextstep.step1.HelloMethodMatcher;
import camp.nextstep.step1.cglib.HelloMethodInterceptor;
import camp.nextstep.step1.cglib.HelloTarget;
import camp.nextstep.step1.dynamic.Hello;
import camp.nextstep.step1.dynamic.HelloInvocationHandler;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CglibProxyTest {
    @Test
    void 프록시를_생성해서_메소드호출시_대문자로_반환한다() {
        // given
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloMethodInterceptor(new HelloMethodMatcher()));
        HelloTarget proxy = (HelloTarget) enhancer.create();


        // when
        String sayHelloResult = proxy.sayHello("a");
        String sayHiResult = proxy.sayHi("b");
        String sayThankYouResult = proxy.sayThankYou("c");

        // then
        assertEquals("HELLO A", sayHelloResult);
        assertEquals("HI B", sayHiResult);
        assertEquals("THANK YOU C", sayThankYouResult);
    }

    @Test
    void say로_시작하는_메소드만_대문자로_반환한다() {
        // given
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new HelloMethodInterceptor(new HelloMethodMatcher()));
        HelloTarget proxy = (HelloTarget) enhancer.create();

        // when
        String sayHelloResult = proxy.sayHello("a");
        String sayHiResult = proxy.sayHi("b");
        String sayThankYouResult = proxy.sayThankYou("c");
        String pingpong = proxy.pingpong("d");

        // then
        assertEquals("HELLO A", sayHelloResult);
        assertEquals("HI B", sayHiResult);
        assertEquals("THANK YOU C", sayThankYouResult);
        assertEquals("Pong d", pingpong);
    }
}
