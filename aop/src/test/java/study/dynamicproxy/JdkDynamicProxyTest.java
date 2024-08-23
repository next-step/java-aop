package study.dynamicproxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.SayMethodMatcher;

public class JdkDynamicProxyTest {
    @DisplayName("JdkDynamicProxy 를 이용해서 프록시를 만들 수 있다.")
    @Test
    void createProxy() {
        HelloTarget helloTarget = new HelloTarget();

        Hello helloProxy = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseInvocationHandler(helloTarget, new SayMethodMatcher())
        );

        assertAll(
                () -> assertThat(helloProxy.sayHello("kim")).isEqualTo("HELLO KIM"),
                () -> assertThat(helloProxy.sayHi("kim")).isEqualTo("HI KIM"),
                () -> assertThat(helloProxy.sayThankYou("kim")).isEqualTo("THANK YOU KIM"),
                () -> assertThat(helloProxy.pingPong("kim")).isEqualTo("Ping Pong kim")
        );
    }
}
