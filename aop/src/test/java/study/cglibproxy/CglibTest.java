package study.cglibproxy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.SayMethodMatcher;

/**
 * CGLIB
 * <pre>
 * - Code Generator Library
 * - 런타임에 동적으로 자바 클래스의 프록시를 생성해주는 기능을 제공
 * - 매우 쉽게 프록시 객체 생성 가능 & 성능 우수
 * - 인터페이스가 아닌, 클래스에 대해 동적 프록시 생성 가능
 * - Hibernate는 자바빈 객체에 대한 프록시를 생성할 때 CGLIB 사용
 * - Spring AOP 구현 시 CGLIB 사용
 * </pre>
 * 구성 요소
 * <pre>
 * - Enhancer
 * - Callback <- MethodInterceptor
 * - CallbackFilter
 * </pre>
 */
public class CglibTest {
    @DisplayName("CGLIB을 이용해서 프록시를 만들 수 있다.")
    @Test
    void createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new UppercaseMethodInterceptor(new SayMethodMatcher()));

        HelloTarget helloProxy = (HelloTarget) enhancer.create();

        assertAll(
                () -> assertThat(helloProxy.sayHello("kim")).isEqualTo("HELLO KIM"),
                () -> assertThat(helloProxy.sayHi("kim")).isEqualTo("HI KIM"),
                () -> assertThat(helloProxy.sayThankYou("kim")).isEqualTo("THANK YOU KIM"),
                () -> assertThat(helloProxy.pingPong("kim")).isEqualTo("Ping Pong kim")
        );
    }
}
