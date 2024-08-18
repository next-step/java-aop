package camp.nextstep.cglib;

import net.sf.cglib.proxy.Enhancer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CGLIB Proxy 테스트")
class CglibProxyTest {

    @DisplayName("프록시 객체를 생성하고 메서드를 호출한다.")
    @Test
    void sayHello() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UpperCaseMethodInterceptor());
        HelloTarget proxiedHello = (HelloTarget) enhancer.create();

        assertThat(proxiedHello.sayHello("test")).isEqualTo("HELLO TEST");
        assertThat(proxiedHello.sayHi("test")).isEqualTo("HI TEST");
        assertThat(proxiedHello.sayThankYou("test")).isEqualTo("THANK YOU TEST");
        assertThat(proxiedHello.pingpong("test")).isEqualTo("Pong test");
    }
}
