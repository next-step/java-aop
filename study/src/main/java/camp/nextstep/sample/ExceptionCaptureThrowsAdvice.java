package camp.nextstep.sample;

import java.lang.reflect.Method;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionCaptureThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception ex) throws Throwable {
        System.out.println("Generic Exception Capture");
        System.out.println("Caught: " + ex.getClass().getName());
    }

    public void afterThrowing(Method method, Object[] args, Object target, IllegalArgumentException ex) throws Throwable {
        System.out.println("IllegalArgumentException Capture");
        System.out.println("Caught: " + ex.getClass().getName());
        System.out.println("Method: " + method.getName());
    }
}
