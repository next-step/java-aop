package camp.nextstep.cglib;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.HelloTarget;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyHelloTargetTest {

    @Test
    @DisplayName("프록시를 사용해 클래스가 RETURN 하는 값을 그대로 return 합니다.")
    void proxyToSame() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(NoOp.INSTANCE);

        Object proxy = enhancer.create();
        HelloTarget helloTarget = (HelloTarget) proxy;

        assertThat(helloTarget.sayHello("simon")).isEqualTo("Hello simon");
        assertThat(helloTarget.sayHi("simon")).isEqualTo("Hi simon");
        assertThat(helloTarget.sayThankYou("simon")).isEqualTo("Thank You simon");
    }

    @Test
    @DisplayName("프록시를 사용해 클래스가 RETURN 하는 값을 대문자로 변경하였습니다.")
    void proxyToCapital() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(HelloTarget.class);
        enhancer.setCallback(new MethodCallLogInterceptor());

        Object proxy = enhancer.create();
        HelloTarget helloTarget = (HelloTarget) proxy;

        assertThat(helloTarget.sayHello("simon")).isEqualTo("HELLO SIMON");
        assertThat(helloTarget.sayHi("simon")).isEqualTo("HI SIMON");
        assertThat(helloTarget.sayThankYou("simon")).isEqualTo("THANK YOU SIMON");
    }


    public class MethodCallLogInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
            throws Throwable {
            Object returnValue = proxy.invokeSuper(obj, args);

            return returnValue.toString().toUpperCase();
        }
    }

}
