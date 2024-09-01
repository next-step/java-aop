package samples;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advice.JoinPoint;

public class HelloAroundAdvice implements AroundAdvice {
    @Override
    public Object invoke(JoinPoint joinPoint) throws Throwable {
        return "Hello, " + joinPoint.proceed() + "!";
    }
}
