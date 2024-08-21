package dynamic;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;

class JdkProxyHelloTest {

    @Test
    void toUpperCase() {
        HelloTarget helloTarget = new HelloTarget();
        ClassLoader classLoader = helloTarget.getClass().getClassLoader();
        Class<?>[] interfaces = helloTarget.getClass().getInterfaces();
        InvocationHandler upperCaseInvocationHandler = new UpperCaseInvocationHandler(helloTarget);

        Hello hello = (Hello) Proxy.newProxyInstance(classLoader, interfaces, upperCaseInvocationHandler);

        assertThat(hello.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(hello.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(hello.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
        assertThat(hello.pingPong("Toby")).isEqualTo("Ping Pong Toby");
    }
}

