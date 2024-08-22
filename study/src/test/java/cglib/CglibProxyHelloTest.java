package cglib;

import static org.assertj.core.api.Assertions.assertThat;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.Test;

class CglibProxyHelloTest {

    @Test
    void upperCase() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CgHelloTarget.class);
        enhancer.setCallback(new UpperCaseMethodInterceptor());
        CgHelloTarget helloTargetProxy = (CgHelloTarget) enhancer.create();

        assertThat(helloTargetProxy.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(helloTargetProxy.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(helloTargetProxy.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
        assertThat(helloTargetProxy.pingPong("Toby")).isEqualTo("Ping Pong Toby");
    }
}
