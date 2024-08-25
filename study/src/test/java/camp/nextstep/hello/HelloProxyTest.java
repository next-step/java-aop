package camp.nextstep.hello;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloProxyTest {
    @Test
    void javaDynamicProxyTest() {
        HelloTarget hello = new HelloTarget();
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{Hello.class},
                (proxy, method, args) -> ((String) method.invoke(hello, args)).toUpperCase());

        assertThat(proxyInstance.sayHello("foo")).isEqualTo("HELLO FOO");
        assertThat(proxyInstance.sayHi("bar")).isEqualTo("HI BAR");
        assertThat(proxyInstance.sayThankYou("가나다")).isEqualTo("THANK YOU 가나다");
    }
}
