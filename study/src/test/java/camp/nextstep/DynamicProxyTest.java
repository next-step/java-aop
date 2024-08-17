package camp.nextstep;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DynamicProxyTest {

    @DisplayName("인터페이스와 구현 클래스를 이용해 모든 메서드의 반환 값을 대문자로 변환 한다")
    @Test
    public void toUpper() throws Exception {
        // given
        final HelloTarget helloTarget = new HelloTarget();

        final Hello hello = (Hello) Proxy.
                newProxyInstance(
                        this.getClass().getClassLoader(),
                        new Class[]{Hello.class},
                        (proxy, method, args) -> method.invoke(helloTarget, args).toString().toUpperCase());

        final String name = "name";

        // when
        final String helloActual = hello.sayHello(name);
        final String hiActual = hello.sayHi(name);
        final String thankYouActual = hello.sayThankYou(name);
        final String pingPongActual = hello.pingPong(name);

        // then
        assertAll(
                () -> assertThat(helloActual).isEqualTo("HELLO NAME"),
                () -> assertThat(hiActual).isEqualTo("HI NAME"),
                () -> assertThat(thankYouActual).isEqualTo("THANK YOU NAME"),
                () -> assertThat(pingPongActual).isEqualTo("PING PONG NAME")
        );
    }

    @DisplayName("인터페이스와 구현 클래스를 이용해 say 로 시작하는 메서드의 반환 값을 대문자로 변환 한다")
    @Test
    public void sayMethodToUpper() throws Exception {
        // given
        final HelloTarget helloTarget = new HelloTarget();

        final Hello hello = (Hello) Proxy.
                newProxyInstance(
                        this.getClass().getClassLoader(),
                        new Class[]{Hello.class},
                        new UpperCaseInvocationHandler(helloTarget));

        final String name = "name";

        // when
        final String helloActual = hello.sayHello(name);
        final String hiActual = hello.sayHi(name);
        final String thankYouActual = hello.sayThankYou(name);
        final String pingPongActual = hello.pingPong(name);

        // then
        assertAll(
                () -> assertThat(helloActual).isEqualTo("HELLO NAME"),
                () -> assertThat(hiActual).isEqualTo("HI NAME"),
                () -> assertThat(thankYouActual).isEqualTo("THANK YOU NAME"),
                () -> assertThat(pingPongActual).isEqualTo("ping Pong name")
        );
    }
}
