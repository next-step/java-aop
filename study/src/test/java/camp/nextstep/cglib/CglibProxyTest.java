package camp.nextstep.cglib;

import com.interface21.beans.factory.ProxyFactoryBean;
import com.interface21.framework.Advised;
import com.interface21.framework.Advisor;
import com.interface21.framework.AfterReturningAdvice;
import com.interface21.framework.MethodInvocation;
import net.sf.cglib.proxy.Enhancer;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CGLIB Proxy 테스트")
class CglibProxyTest {

    @DisplayName("say로 시작하는 메서드의 반환 값을 대문자로 변환한다.")
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

    @DisplayName("ProxyFactoryBean으로 만들어진 프록시를 사용하여 say로 시작하는 메서드의 반환 값을 대문자로 변환한다.")
    @Test
    void sayHelloWithAopProxyFactory() {

        HelloTarget proxiedHello = createProxiedHelloTarget();

        assertThat(proxiedHello.sayHello("test")).isEqualTo("HELLO TEST");
        assertThat(proxiedHello.sayHi("test")).isEqualTo("HI TEST");
        assertThat(proxiedHello.sayThankYou("test")).isEqualTo("THANK YOU TEST");
        assertThat(proxiedHello.pingpong("test")).isEqualTo("Pong test");
    }

    private HelloTarget createProxiedHelloTarget() {
        return (HelloTarget) new ProxyFactoryBean(
            new Advised(
                HelloTarget.class,
                new Advisor(( method ) -> method.getName().startsWith("say"), new UpperCaseAdvice())
            )
        ).getObject();
    }


    private static class UpperCaseAdvice implements AfterReturningAdvice {

        @Override
        public void invoke(MethodInvocation invocation) throws Throwable {
            String result = (String) invocation.getReturnValue();
            invocation.setReturnValue(result.toUpperCase());
        }
    }
}
