package camp.nextstep.hello;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloProxyTest {
    @Test
    @DisplayName("Java Dynamic Proxy 적용")
    void javaDynamicProxyTest() {
        HelloTarget target = new HelloTarget();
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{Hello.class},
                (proxy, method, args) -> {
                    if (method.getName().startsWith("say")) {
                        return ((String) method.invoke(target, args)).toUpperCase();
                    }
                    return method.invoke(target, args);
                });

        assertThat(proxyInstance.sayHello("foo")).isEqualTo("HELLO FOO");
        assertThat(proxyInstance.sayHi("bar")).isEqualTo("HI BAR");
        assertThat(proxyInstance.sayThankYou("가나다")).isEqualTo("THANK YOU 가나다");
        assertThat(proxyInstance.ping("foo")).isEqualTo("Pong foo");
    }

    @Test
    @DisplayName("cglib Proxy 적용")
    void cglibProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getName().startsWith("say")) {
                return ((String) proxy.invokeSuper(obj, args)).toUpperCase();
            }
            return proxy.invokeSuper(obj, args);
        });
        Object obj = enhancer.create();
        HelloTarget proxyInstance = (HelloTarget) obj;

        assertThat(proxyInstance.sayHello("foo")).isEqualTo("HELLO FOO");
        assertThat(proxyInstance.sayHi("bar")).isEqualTo("HI BAR");
        assertThat(proxyInstance.sayThankYou("가나다")).isEqualTo("THANK YOU 가나다");
        assertThat(proxyInstance.ping("foo")).isEqualTo("Pong foo");
    }
}
