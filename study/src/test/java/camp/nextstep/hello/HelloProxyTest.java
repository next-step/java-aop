package camp.nextstep.hello;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloProxyTest {
    @Test
    void javaDynamicProxyTest() {
        HelloTarget hello = new HelloTarget();
        Hello proxyInstance = (Hello) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{Hello.class},
                (proxy, method, args) -> ((String) method.invoke(hello, args)).toUpperCase());

        assertThat(proxyInstance.sayHello("foo")).isEqualTo("HELLO FOO");
        assertThat(proxyInstance.sayHi("bar")).isEqualTo("HI BAR");
        assertThat(proxyInstance.sayThankYou("가나다")).isEqualTo("THANK YOU 가나다");
    }

    @Test
    void cglibProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
            if (method.getName().startsWith("say")) {
                return ((String) proxy.invokeSuper(obj, args)).toUpperCase();
            }
            return NoOp.INSTANCE;
        });
        Object obj = enhancer.create();
        HelloTarget proxyInstance = (HelloTarget) obj;

        assertThat(proxyInstance.sayHello("foo")).isEqualTo("HELLO FOO");
        assertThat(proxyInstance.sayHi("bar")).isEqualTo("HI BAR");
        assertThat(proxyInstance.sayThankYou("가나다")).isEqualTo("THANK YOU 가나다");
    }
}
