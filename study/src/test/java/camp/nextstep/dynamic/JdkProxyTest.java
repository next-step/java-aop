package camp.nextstep.dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.Hello;
import camp.nextstep.HelloTarget;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;


public class JdkProxyTest {

    @Test
    void toUppercase() {
        HelloTarget helloTarget = new HelloTarget();

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{Hello.class},
            new UpperCaseMethodHandler(helloTarget));
        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
    }
}

