package camp.nextstep.dynamic;

import java.lang.reflect.Proxy;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("JDK Dynamic Proxy 테스트")
class JdkProxyTest {

    @DisplayName("프록시 객체를 생성하고 메서드를 호출한다.")
    @Test
    void toUppercase() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[] { Hello.class},
            new UpperCaseInvocationHandler(new HelloTarget()));
        assertThat(proxiedHello.sayHello("test")).isEqualTo("HELLO TEST");
        assertThat(proxiedHello.sayHi("test")).isEqualTo("HI TEST");
        assertThat(proxiedHello.sayThankYou("test")).isEqualTo("THANK YOU TEST");
        assertThat(proxiedHello.pingpong("test")).isEqualTo("Pong test");
    }
}
