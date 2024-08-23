package study.dynamicproxy;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.reflect.Proxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JdkDynamicProxyTest {
    @DisplayName("JdkDynamicProxy 를 이용해서 프록시를 만들 수 있다.")
    @Test
    void createProxy() {
        HelloTarget helloTarget = new HelloTarget();

        Hello helloProxy = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseInvocationHandler(helloTarget)
        );

        assertAll(
                () -> Assertions.assertThat(helloProxy.sayHello("kim")).isEqualTo("HELLO KIM"),
                () -> Assertions.assertThat(helloProxy.sayHi("kim")).isEqualTo("HI KIM"),
                () -> Assertions.assertThat(helloProxy.sayThankYou("kim")).isEqualTo("THANK YOU KIM")
        );
    }
}
