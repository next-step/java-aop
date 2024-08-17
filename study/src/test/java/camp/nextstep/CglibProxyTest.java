package camp.nextstep;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CglibProxyTest {

    @DisplayName("구현 클래스만 이용해 모든 메서드의 반환 값을 대문자로 변환 한다")
    @Test
    public void upperCase() throws Exception {
        // given
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(o, objects).toString().toUpperCase());
        final HelloTarget helloTarget = (HelloTarget) enhancer.create();

        final String name = "name";

        // when
        final String helloActual = helloTarget.sayHello(name);
        final String hiActual = helloTarget.sayHi(name);
        final String thankYouActual = helloTarget.sayThankYou(name);
        final String pingPongActual = helloTarget.pingPong(name);

        // then
        assertAll(
                () -> assertThat(helloActual).isEqualTo("HELLO NAME"),
                () -> assertThat(hiActual).isEqualTo("HI NAME"),
                () -> assertThat(thankYouActual).isEqualTo("THANK YOU NAME"),
                () -> assertThat(pingPongActual).isEqualTo("PING PONG NAME")
        );
    }
}
